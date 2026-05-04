package com.example.projet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
imporemna.mnejja1808@gmail.comt org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    /**
     * Send confirmation email when a quote request is created
     */
    @Async("taskExecutor")
    public void sendQuoteRequestConfirmation(String toEmail, String firstName, String lastName, String token) {
        System.out.println("=== EMAIL METHOD ENTERED ===");
        System.out.println("Thread: " + Thread.currentThread().getName());
        System.out.println("To: " + toEmail);
        
        try {
            logger.info("Starting to send quote request confirmation email to: {} (Thread: {})", 
                toEmail, Thread.currentThread().getName());
            logger.debug("Email config - From: {}, Frontend URL: {}", fromEmail, frontendUrl);
            
            if (mailSender == null) {
                logger.error("CRITICAL: mailSender is NULL!");
                System.err.println("=== MAIL SENDER IS NULL ===");
                return;
            }
            
            if (fromEmail == null || fromEmail.isEmpty()) {
                logger.error("CRITICAL: fromEmail is not configured!");
                System.err.println("=== FROM EMAIL NOT CONFIGURED ===");
                return;
            }
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Your Quote Request - TunisiaMed");

            String viewOffersUrl = frontendUrl + "/view-offers?token=" + token;
            logger.debug("View offers URL: {}", viewOffersUrl);

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
            
            logger.info("Attempting to send email via SMTP...");
            System.out.println("=== CALLING MAIL SENDER ===");
            mailSender.send(message);
            System.out.println("=== MAIL SENDER RETURNED ===");
            
            logger.info("✓ Quote request confirmation email sent successfully to: {}", toEmail);

        } catch (MessagingException e) {
            logger.error("✗ Failed to send quote request confirmation email to: {} - Error: {}", 
                toEmail, e.getMessage(), e);
            System.err.println("=== MESSAGING EXCEPTION: " + e.getMessage() + " ===");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("✗ Unexpected error sending email to: {} - Error: {}", 
                toEmail, e.getMessage(), e);
            System.err.println("=== UNEXPECTED EXCEPTION: " + e.getMessage() + " ===");
            e.printStackTrace();
        }
    }

    /**
     * Send notification email when a new offer is received
     */
    @Async("taskExecutor")
    public void sendNewOfferNotification(String toEmail, String firstName, String lastName, String clinicName, String token) {
        try {
            logger.info("Starting to send new offer notification email to: {} (Thread: {})", 
                toEmail, Thread.currentThread().getName());
            
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
            
            logger.info("Attempting to send email via SMTP...");
            mailSender.send(message);
            
            logger.info("✓ New offer notification email sent successfully to: {}", toEmail);

        } catch (MessagingException e) {
            logger.error("✗ Failed to send new offer notification email to: {} - Error: {}", 
                toEmail, e.getMessage(), e);
        } catch (Exception e) {
            logger.error("✗ Unexpected error sending email to: {} - Error: {}", 
                toEmail, e.getMessage(), e);
        }
    }
}
