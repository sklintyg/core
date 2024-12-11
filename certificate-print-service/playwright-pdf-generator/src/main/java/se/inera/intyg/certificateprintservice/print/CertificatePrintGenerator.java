package se.inera.intyg.certificateprintservice.print;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.stereotype.Service;

@Service
public class CertificatePrintGenerator implements PrintCertificateGenerator {

  @Override
  public byte[] generate() {
    try (
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium()
            .launch(new BrowserType.LaunchOptions().setHeadless(true));
        BrowserContext context = browser.newContext();
        Page page = context.newPage()
    ) {
      page.setContent(
          """
              <!DOCTYPE html>
              <html lang="en">
              <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>Invoice</title>
                  <style>
                      body {
                          font-family: Arial, sans-serif;
                          margin: 0;
                          padding: 20px;
                      }
                      .container {
                          max-width: 600px;
                          margin: 0 auto;
                          border: 1px solid #ccc;
                          padding: 20px;
                          border-radius: 8px;
                      }
                      h1, h2 {
                          text-align: center;
                      }
                      table {
                          width: 100%;
                          border-collapse: collapse;
                          margin-top: 20px;
                      }
                      th, td {
                          border: 1px solid #ccc;
                          padding: 8px;
                          text-align: left;
                      }
                      .total {
                          margin-top: 20px;
                          text-align: right;
                      }
                  </style>
              </head>
              <body>
                  <div class="container">
                      <h1>Invoice</h1>
                      <p><strong>Invoice Number:</strong> #123456</p>
                      <p><strong>Date:</strong> February 23, 2024</p>
                      <p><strong>Bill To:</strong> John Doe</p>
                      <table>
                          <thead>
                              <tr>
                                  <th>Description</th>
                                  <th>Quantity</th>
                                  <th>Unit Price</th>
                                  <th>Total</th>
                              </tr>
                          </thead>
                          <tbody>
                              <tr>
                                  <td>Product 1</td>
                                  <td>2</td>
                                  <td>$20.00</td>
                                  <td>$40.00</td>
                              </tr>
                              <tr>
                                  <td>Product 2</td>
                                  <td>1</td>
                                  <td>$30.00</td>
                                  <td>$30.00</td>
                              </tr>
                          </tbody>
                      </table>
                      <div class="total">
                          <p><strong>Total:</strong> $70.00</p>
                      </div>
                  </div>
              </body>
              </html>
              """
      );

      return page.pdf();
    }
  }
}