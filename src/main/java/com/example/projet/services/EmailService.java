package com.example.projet.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.List;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final String RESEND_API_URL = "https://api.resend.com/emails";

    @Value("${resend.api.key}")
    private String resendApiKey;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    private final RestClient restClient;

    public EmailService() {
        this.restClient = RestClient.create();
    }

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
            
            if (resendApiKey == null || resendApiKey.isEmpty()) {
                logger.error("CRITICAL: Resend API key is not configured!");
                System.err.println("=== RESEND API KEY NOT CONFIGURED ===");
                return;
            }

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

            Map<String, Object> emailRequest = Map.of(
                "from", "TunisiaMed <onboarding@resend.dev>",
                "to", List.of(toEmail),
                "subject", "Your Quote Request - TunisiaMed",
                "html", htmlContent
            );

            logger.info("Attempting to send email via Resend API...");
            System.out.println("=== CALLING RESEND API ===");
            
            String response = restClient.post()
                .uri(RESEND_API_URL)
                .header("Authorization", "Bearer " + resendApiKey)
                .header("Content-Type", "application/json")
                .body(emailRequest)
                .retrieve()
                .body(String.class);
            
            System.out.println("=== RESEND API RETURNED ===");
            logger.info("✓ Quote request confirmation email sent successfully to: {} - Response: {}", toEmail, response);

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
            
            if (resendApiKey == null || resendApiKey.isEmpty()) {
                logger.error("CRITICAL: Resend API key is not configured!");
                return;
            }

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

            Map<String, Object> emailRequest = Map.of(
                "from", "TunisiaMed <onboarding@resend.dev>",
                "to", List.of(toEmail),
                "subject", "New Offer Received - TunisiaMed",
                "html", htmlContent
            );

            logger.info("Attempting to send email via Resend API...");
            
            String response = restClient.post()
                .uri(RESEND_API_URL)
                .header("Authorization", "Bearer " + resendApiKey)
                .header("Content-Type", "application/json")
                .body(emailRequest)
                .retrieve()
                .body(String.class);
            
            logger.info("✓ New offer notification email sent successfully to: {} - Response: {}", toEmail, response);

        } catch (Exception e) {
            logger.error("✗ Unexpected error sending email to: {} - Error: {}", 
                toEmail, e.getMessage(), e);
        }
    }
}
