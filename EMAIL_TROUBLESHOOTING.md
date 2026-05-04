# Email Troubleshooting Guide for Render Deployment

## Changes Made
1. ✅ Added `@Async` with custom executor to prevent blocking
2. ✅ Added comprehensive logging to track email sending
3. ✅ Added error handling so email failures don't break API
4. ✅ Added SMTP timeout configurations
5. ✅ Enabled mail debug mode

## Check Render Logs

After deploying, check your Render logs for these messages:

### Success Pattern:
```
Starting to send quote request confirmation email to: user@example.com (Thread: async-email-1)
Attempting to send email via SMTP...
✓ Quote request confirmation email sent successfully to: user@example.com
```

### Failure Patterns:

**1. SMTP Connection Timeout:**
```
✗ Failed to send quote request confirmation email - Error: Connection timed out
```
**Solution:** Render might be blocking port 587. Try using port 465 with SSL.

**2. Authentication Failed:**
```
✗ Failed to send quote request confirmation email - Error: 535 Authentication failed
```
**Solution:** 
- Enable "Less secure app access" in Gmail (not recommended)
- Use Gmail App Password instead
- Switch to a transactional email service

**3. No logs at all:**
The async task might not be running. Check if `AsyncConfig` is loaded.

## Recommended Solutions for Production

### Option 1: Use Gmail App Password (Recommended for testing)
1. Go to Google Account → Security
2. Enable 2-Step Verification
3. Generate an App Password
4. Use that password in Render environment variables

### Option 2: Try Port 465 with SSL
Update in Render environment variables:
```
SPRING_MAIL_PORT=465
SPRING_MAIL_PROPERTIES_MAIL_SMTP_SSL_ENABLE=true
SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=false
```

### Option 3: Use Professional Email Service (Best for Production)

**SendGrid (Free tier: 100 emails/day):**
```properties
spring.mail.host=smtp.sendgrid.net
spring.mail.port=587
spring.mail.username=apikey
spring.mail.password=YOUR_SENDGRID_API_KEY
```

**Mailgun (Free tier: 5,000 emails/month):**
```properties
spring.mail.host=smtp.mailgun.org
spring.mail.port=587
spring.mail.username=postmaster@your-domain.mailgun.org
spring.mail.password=YOUR_MAILGUN_PASSWORD
```

**AWS SES (Very cheap, $0.10 per 1000 emails):**
```properties
spring.mail.host=email-smtp.us-east-1.amazonaws.com
spring.mail.port=587
spring.mail.username=YOUR_SMTP_USERNAME
spring.mail.password=YOUR_SMTP_PASSWORD
```

## Environment Variables for Render

Set these in Render Dashboard → Environment:

```
SPRING_MAIL_HOST=smtp.gmail.com
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=tunisiamed34@gmail.com
SPRING_MAIL_PASSWORD=your-app-password-here
SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true
APP_FRONTEND_URL=https://emnamnejja6.github.io
```

## Testing After Deployment

1. Submit a quote request from your UI
2. Check Render logs immediately
3. Look for the email logging messages
4. If you see connection errors, try the alternative ports/services above

## Quick Test Command

Test email from Render shell:
```bash
curl -X POST https://your-app.onrender.com/api/quote-requests \
  -H "Content-Type: application/json" \
  -d '{
    "fname": "Test",
    "lname": "User",
    "email": "your-email@gmail.com",
    "phone": "+123456789",
    "country": "Tunisia",
    "dateofBirth": "1990-01-01",
    "description": "Test",
    "specialtyId": 2
  }'
```

Then immediately check logs for email status.
