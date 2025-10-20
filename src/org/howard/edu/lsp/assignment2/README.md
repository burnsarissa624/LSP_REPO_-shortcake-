# CSCI 363/540 – Assignment 1: CSV ETL Pipeline (Java)

## Project Structure
src/
└── org/
    └── howard/
        └── edu/
            └── lsp/
                └── assignment2/
                    └── ETLPipeline.java
data/
├── products.csv
└── transformed_products.csv

## What this project does
This program is a simple ETL (Extract, Transform, Load) pipeline written in Java. It reads data from a CSV file, makes changes based on the assignment rules, and then writes the results into a new CSV file.

- Extract: read the products from data/products.csv  
- Transform:  
  - Make product names uppercase  
  - Take 10% off Electronics prices  
  - If the discounted Electronics price is over $500, change category to Premium Electronics  
  - Add a PriceRange column (Low, Medium, High, Premium)  
- Load: write everything into data/transformed_products.csv

## Requirements
- Java 8 or higher (I used Java 17)  
- Run from the project root (the folder that has src/ and data/)  

## How to Run
```bash
mkdir -p out
javac -d out -sourcepath src src/org/howard/edu/lsp/assignment2/ETLPipeline.java
java -cp out org.howard.edu.lsp.assignment2.ETLPipeline

```

## Description

This project implements a simple ETL (Extract, Transform, Load) pipeline in Java for processing CSV files.

- **Extract:** Reads data from a CSV file.
- **Transform:** Processes and cleans the data.
- **Load:** Outputs the transformed data.

## Requirements

- Java 8 or higher

## Design Notes

I split the code into methods: extract, transform, load.
The transform order follows the assignment exactly:
Convert product names to uppercase
Apply a 10% discount if the category is Electronics
If the discounted Electronics price is over $500, recategorize to Premium Electronics
Add a PriceRange column based on the final price
Prices are rounded to two decimals using BigDecimal with RoundingMode.HALF_UP.

# Error Handling 
If the file is missing, the program shows a clear error message
If the file only has a header, it still creates an output with just the header row
If rows are missing data or have bad numbers, they are skipped

# Testing
Normal case: tested with 6 products, output matched the golden sample
Empty input: only header in the input file, so only header in the output
Missing file: program shows error and stops without crashing
The run summary shows rows read, transformed, skipped, and the output path.

# AI Usage 
I used ChatGPT to help me set up the project step by step and to get ideas for how to structure the ETL into methods. I wrote and tested the final Java code myself.
Example prompt I asked:
“Write a plain-Java CSV ETL that reads data/products.csv, applies uppercase/discount/recategorize/price-range in order, handles missing/empty file, and writes data/transformed_products.csv.”
What I used: the suggestion to use BigDecimal.setScale(2, RoundingMode.HALF_UP) for rounding and to separate the program into extract, transform, and load methods. I adapted it to fit the assignment exactly.

# External Sources 
I didn’t use outside websites besides Java’s own documentation. If I add anything later, I will cite it.