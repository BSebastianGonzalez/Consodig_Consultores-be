package com.cc.be.service;

import com.cc.be.model.Account;
import com.cc.be.model.PasswordResetToken;
import com.cc.be.repository.AccountRepository;
import com.cc.be.repository.PasswordResetTokenRepository;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final AccountRepository accountRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TemplateEngine templateEngine;

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    @Value("${sendgrid.from-name}")
    private String fromName;

    public void sendResetLink(String correo) {
        Account account = accountRepository.findByEmail(correo)
                .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(
                token, account, LocalDateTime.now().plusMinutes(15)
        );
        tokenRepository.save(resetToken);

        String link = "https://consodigconsultores.vercel.app/reset-password-confirm?token=" + token;

        Context context = new Context();
        context.setVariable("link", link);
        String htmlContent = templateEngine.process("reset-password-email", context);

        sendEmail(correo, "Restablecimiento de contraseña", htmlContent);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido o expirado"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado");
        }

        Account account = resetToken.getAccount();
        account.setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(account);

        tokenRepository.delete(resetToken);

        Context context = new Context();
        String htmlContent = templateEngine.process("password-updated-email", context);

        sendEmail(account.getEmail(), "Contraseña actualizada", htmlContent);
    }

    private void sendEmail(String toEmail, String subject, String htmlContent) {
        Email from = new Email(fromEmail, fromName);
        Email to = new Email(toEmail);
        Content content = new Content("text/html", htmlContent);
        Mail mail = new Mail(from, subject, to, content);

        try {
            // 📎 Cargar logo embebido desde resources
            ClassPathResource resource = new ClassPathResource("static/images/logo.png");
            byte[] imageBytes = resource.getInputStream().readAllBytes();

            Attachments logoAttachment = new Attachments();
            logoAttachment.setContent(Base64.getEncoder().encodeToString(imageBytes));
            logoAttachment.setType("image/png");
            logoAttachment.setFilename("logo.png");
            logoAttachment.setDisposition("inline");
            logoAttachment.setContentId("platformLogo");

            mail.addAttachments(logoAttachment);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);

        } catch (IOException e) {
            throw new RuntimeException("Error al enviar el correo con SendGrid: " + e.getMessage());
        }
    }
}
