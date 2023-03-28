package com.adaptnxt.tech;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.opencsv.CSVWriter;

public class ReadDataFromURL {

	public static void main(String[] args) {
		
		String url = "https://www.staples.com/Computer-office-desks/cat_CL210795/663ea?icid=BTS:2020:STUDYSPACE:DESKS";
        String csvFilePath = "C:\\Users\\rakis\\Documents\\workspace-sts-3.9.12.RELEASE\\Adaptnxt_Projact1\\ProductDetails.csv";
        int maxProducts = 10; // Maximum number of products to scrape
        
        try {
            // Connect to the website and get the HTML
            Document doc = Jsoup.connect(url).get();
            
            // Get all the product elements on the page
            Elements products = doc.select(".product-item");
            
            // Create a new CSV writer and write the header row
            CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath));
            String[] header = {"Product Name", "Product Price", "Item Number", "Model Number", "Product Category", "Product Description"};
            writer.writeNext(header);
            
            // Iterate over each product and write its details to the CSV file
            int countProduct = 0;
            for (Element product : products) {
                if (countProduct >= maxProducts) {
                    break;
                }
                String productName = product.select(".product-title").text();
                String productPrice = product.select(".price").text();
                String productCode = product.attr("data-code");
                String modelNumber = product.attr("data-model-number");
                String productCategory = product.attr("data-category");
                String productDescription = product.select(".product-desc").text();
                String[] row = {productName, productPrice, productCode, modelNumber, productCategory, productDescription};
                writer.writeNext(row);
                countProduct++;
            }
            
            
            
            // Close the CSV writer
            writer.close();
            
            System.out.println("Product details are successfully stored into CSV file.");
            
        } catch (IOException e) {
            e.printStackTrace();
        }

	}

}
