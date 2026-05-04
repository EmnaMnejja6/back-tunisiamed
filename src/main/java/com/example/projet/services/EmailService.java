package com.example.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Send confirmation email when a quote request is created
     */
    public void sendQuoteRequestConfirmation(String toEmail, String firstName, String lastName, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Your Quote Request - TunisiaMed");

            String viewOffersUrl = frontendUrl + "/view-offers?token=" + token;

            String htmlContent = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background-color: #0d9488; color: white; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; }
                        .content { background-color: #f9fafb; padding: 30px; border-radius: 0 0 8px 8px; }
                        .button { display: inline-block; background-color: #0d9488; color: white; padding: 12px 30px; text-decoration: none; border-radius: 6px; margin: 20px 0; }
                        .footer { text-align: center; margin-top: 20px; color: #6b7280; font-size: 14px; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>TunisiaMed</h1>
                        </div>
                        <div class="content">
                            <h2>Dear %s %s,</h2>
                            <p>Thank you for submitting your quote request!</p>
                            <p>Your request has been received and clinics will start reviewing it shortly. You will receive offers from interested clinics.</p>
                            <p>Click the button below to view your offers:</p>
                            <div style="text-align: center;">
                                <a href="%s" class="button">View Your Offers</a>
                            </div>
                            <p>Or copy this link: <a href="%s">%s</a></p>
                            <p><strong>Important:</strong> Save this link to check for new offers at any time.</p>
                        </div>
                        <div class="footer">
                            <p>© 2026 TunisiaMed. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
                """, firstName, lastName, viewOffersUrl, viewOffersUrl, viewOffersUrl);

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Send notification email when a new offer is received
     */
    public void sendNewOfferNotification(String toEmail, String firstName, String lastName, String clinicName, String token) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("New Offer Received - TunisiaMed");

            String viewOffersUrl = frontendUrl + "/view-offers?token=" + token;

            String htmlContent = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                        .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                        .header { background-color: #0d9488; color: white; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; }
                        .content { background-color: #f9fafb; padding: 30px; border-radius: 0 0 8px 8px; }
                        .button { display: inline-block; background-color: #0d9488; color: white; padding: 12px 30px; text-decoration: none; border-radius: 6px; margin: 20px 0; }
                        .footer { text-align: center; margin-top: 20px; color: #6b7280; font-size: 14px; }
                        .highlight { background-color: #d1fae5; padding: 15px; border-radius: 6px; margin: 15px 0; }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h1>TunisiaMed</h1>
                        </div>
                        <div class="content">
                            <h2>Dear %s %s,</h2>
                            <div class="highlight">
                                <p><strong>Good news!</strong> You have received a new offer from <strong>%s</strong>.</p>
                            </div>
                            <p>Click the button below to view all your offers:</p>
                            <div style="text-align: center;">
                                <a href="%s" class="button">View Your Offers</a>
                            </div>
                        </div>
                        <div class="footer">
                            <p>© 2026 TunisiaMed. All rights reserved.</p>
                        </div>
                    </div>
                </body>
                </html>
                """, firstName, lastName, clinicName, viewOffersUrl);

            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
