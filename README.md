# Vector Space Model
This project is a vector space model implementation in Java that ranks given files based on the revelance to a given query. The project uses the **Vector Space Model (VSM)** to compute the relevance of text documents to a given search query, **Porter Stemmer** algorithm to perform stemming on the terms, a list of common stop words for performing stop word removal. 

## Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven

## Installation
1. **Clone the repository:**
   ```bash
   git https://github.com/firo1919/Vector-space-model.git
   cd Vector-space-model
   ```

2. **Build the project:**
   ```bash
   mvn clean compile assembly:single
   ```

## Usage
To run the project, use the following command while being inside the directory:
- **If search term is typed manually**
```bash
java -jar target/*.jar <file1> <file2> <file3> ... -q "<query>"
```
- **If search term is from file**
```bash
java -jar target/*.jar <file1> <file2> <file3> ... -f "<file>"
```

### Example
```bash
java -jar target/*.jar ~/Downloads/AI.txt ~/Downloads/Renewable.txt ~/Downloads/opensource.txt ~/Downloads/mentalhealth.txt -q "artificial intelligence and machine learning"
```

In this example, the program will rank the files `AI.txt`, `Renewable.txt`, `opensource.txt`, and `mentalhealth.txt` high to low similarity score,
based on the relevance to the query "artificial intelligence and machine learning" and outputs it to the console.

![Vector Space Model Diagram](preview.png)
