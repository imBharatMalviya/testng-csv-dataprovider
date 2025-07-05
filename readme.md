# TestNG DataProvider with Custom Annotation and CSV Support

**Read the full article on Medium:**  
[TestNG DataProvider with custom annotation](https://medium.com/p/5dc1e4109fb6)

This repo shows how to use a custom annotation (`@TestDataFile`) to feed test data from CSV files into TestNG tests
using a generic, reusable `@DataProvider`.

---

## ✅ What This Does

- Injects CSV test data into TestNG tests with zero hardcoding
- Uses a custom annotation to define the file and target POJO
- Maps CSV rows to Java objects using Jackson
- Returns `Iterator<Object>` for better memory efficiency
- Clean test code no boilerplate or manual parsing

---

## 📂 Project Structure

```
src
├── main
│   └── java
│       └── com/github/imbharatmalviya
│           ├── CsvDataProvider.java
│           ├── CsvUtil.java
│           ├── LoginTestData.java
│           └── TestDataFile.java
└── test
    ├── java
    │   └── com/example/tests
    │       └──  AuthenticationTest.java
    └── resources
        ├── testng.xml
        └── login-tests.csv
```

---

## ▶️ How to Run

1. **Clone the repo**

   ```bash
   git clone https://github.com/imbharatmalviya/testng-csv-dataprovider.git
   cd testng-csv-dataprovider
   ```

2. **Run the tests**

   ```bash
   mvn clean install
   ```

Make sure Java and Maven are installed.

---

## ✍️ Example Test

```java

@Test(dataProvider = "csvDataProvider")
@TestDataFile(fileName = "login-tests.csv", clazz = LoginTestData.class)
public void loginTest(LoginTestData testData) {
    log.info("loginTestData: {}", testData);
}
```

---

## 📄 Sample CSV (`src/test/resources/login-tests.csv`)

```
username,password,expectedResult
user1,pass1,success
user2,wrongpass,failure
```

---

## 🔧 Core Components

### `@TestDataFile` – Custom Annotation

```java

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestDataFile {
    String fileName();

    Class<?> clazz();
}
```

---

### `CsvDataProvider` – Generic DataProvider

```java

@DataProvider(name = "csvDataProvider", parallel = true)
public static Iterator<Object> provideData(Method method) {
    TestDataFile annotation = method.getAnnotation(TestDataFile.class);
    if (annotation == null) {
        throw new RuntimeException("Missing @TestDataFile annotation");
    }
    String fileName = annotation.fileName();
    Class dataClass = annotation.clazz();
    List<Object> dataList = CsvUtil.readCsv(fileName, dataClass);
    return dataList.iterator();
}
```

---

### `CsvUtil` – CSV Reader with Jackson

```java
public class CsvUtil {

    public static <T> List<T> readCsv(String fileName, Class<T> clazz) {
        try (InputStream is = CsvUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            ObjectReader reader = mapper.readerFor(clazz).with(schema);
            return reader.<T>readValues(is).readAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read CSV file: " + fileName, e);
        }
    }
}
```

---

### `LoginTestData` – Sample POJO

```java

@Data
public class LoginTestData {
    private String username;
    private String password;
    private String expectedResult;
}
```

---

## 📦 Dependencies

- TestNG
- Jackson (for CSV deserialization)
- Lombok (for `@Data`)

---

## 🧠 Why Use This Pattern?

- **No boilerplate**: Tests are clean and focused only on logic
- **Reusability**: One provider for all CSV-driven tests
- **Separation of concerns**: Test data stays outside test logic
- **Scalable**: Just drop new CSVs + POJOs + test methods

---

## 📜 License

MIT – Use it, modify it, share it. Credit appreciated but not required.