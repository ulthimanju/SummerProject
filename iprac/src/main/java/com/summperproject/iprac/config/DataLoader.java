package com.summperproject.iprac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.summperproject.iprac.entity.Exercise;
import com.summperproject.iprac.entity.Language;
import com.summperproject.iprac.entity.Topic;
import com.summperproject.iprac.entity.User;
import com.summperproject.iprac.repository.ExerciseRepository;
import com.summperproject.iprac.repository.LanguageRepository;
import com.summperproject.iprac.repository.TopicRepository;
import com.summperproject.iprac.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LanguageRepository languageRepository;
    private final TopicRepository topicRepository;
    private final ExerciseRepository exerciseRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository,
                      LanguageRepository languageRepository,
                      TopicRepository topicRepository,
                      ExerciseRepository exerciseRepository,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.topicRepository = topicRepository;
        this.exerciseRepository = exerciseRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Create admin user if it doesn't exist
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@iprac.com");
            admin.setFullName("Admin User");
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);

            // Create a regular user
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setEmail("user@iprac.com");
            user.setFullName("Regular User");
            user.setRole(User.Role.USER);
            userRepository.save(user);

            // Create sample programming languages
            Language java = new Language();
            java.setName("Java");
            java.setDescription("A high-level, class-based, object-oriented programming language.");
            java.setCompilerOptions("-Xlint:all");
            languageRepository.save(java);

            Language python = new Language();
            python.setName("Python");
            python.setDescription("An interpreted high-level general-purpose programming language.");
            python.setCompilerOptions("-Wall");
            languageRepository.save(python);

            Language javascript = new Language();
            javascript.setName("JavaScript");
            javascript.setDescription("A lightweight, interpreted programming language with first-class functions.");
            javascript.setCompilerOptions("--strict");
            languageRepository.save(javascript);

            // Create topics for Java
            Topic javaBasics = new Topic();
            javaBasics.setName("Java Basics");
            javaBasics.setDescription("Fundamental concepts of Java programming");
            javaBasics.setLanguage(java);
            javaBasics.setDifficultyLevel(1);
            javaBasics.setOrderIndex(1);
            topicRepository.save(javaBasics);

            Topic javaOOP = new Topic();
            javaOOP.setName("Object-Oriented Programming in Java");
            javaOOP.setDescription("Learn OOP principles with Java");
            javaOOP.setLanguage(java);
            javaOOP.setDifficultyLevel(2);
            javaOOP.setOrderIndex(2);
            topicRepository.save(javaOOP);

            // Create exercises for Java Basics
            Exercise helloWorld = new Exercise();
            helloWorld.setTitle("Hello World");
            helloWorld.setDescription("Create a program that prints 'Hello, World!' to the console.");
            helloWorld.setTopic(javaBasics);
            helloWorld.setDifficultyLevel(1);
            helloWorld.setStarterCode("public class HelloWorld {\n\n  public static void main(String[] args) {\n    // Your code here\n  }\n\n}");
            helloWorld.setSolutionCode("public class HelloWorld {\n\n  public static void main(String[] args) {\n    System.out.println(\"Hello, World!\");\n  }\n\n}");
            helloWorld.setTestCases("// Test case 1\n// Expected output: Hello, World!");
            exerciseRepository.save(helloWorld);

            Exercise sumOfTwoNumbers = new Exercise();
            sumOfTwoNumbers.setTitle("Sum of Two Numbers");
            sumOfTwoNumbers.setDescription("Write a function that returns the sum of two integers.");
            sumOfTwoNumbers.setTopic(javaBasics);
            sumOfTwoNumbers.setDifficultyLevel(1);
            sumOfTwoNumbers.setStarterCode("public class SumCalculator {\n\n  public static int sum(int a, int b) {\n    // Your code here\n    return 0;\n  }\n\n  public static void main(String[] args) {\n    System.out.println(sum(5, 3));\n  }\n\n}");
            sumOfTwoNumbers.setSolutionCode("public class SumCalculator {\n\n  public static int sum(int a, int b) {\n    return a + b;\n  }\n\n  public static void main(String[] args) {\n    System.out.println(sum(5, 3));\n  }\n\n}");
            sumOfTwoNumbers.setTestCases("// Test case 1\n// Input: 5, 3\n// Expected output: 8\n\n// Test case 2\n// Input: -1, 1\n// Expected output: 0");
            exerciseRepository.save(sumOfTwoNumbers);

            // Create a few OOP exercises
            Exercise simpleClass = new Exercise();
            simpleClass.setTitle("Create a Simple Class");
            simpleClass.setDescription("Create a Person class with name and age properties, and getter/setter methods.");
            simpleClass.setTopic(javaOOP);
            simpleClass.setDifficultyLevel(2);
            simpleClass.setStarterCode("// Create the Person class here");
            simpleClass.setSolutionCode("public class Person {\n  private String name;\n  private int age;\n\n  public Person(String name, int age) {\n    this.name = name;\n    this.age = age;\n  }\n\n  public String getName() {\n    return name;\n  }\n\n  public void setName(String name) {\n    this.name = name;\n  }\n\n  public int getAge() {\n    return age;\n  }\n\n  public void setAge(int age) {\n    this.age = age;\n  }\n}");
            simpleClass.setTestCases("// Test creating a Person and accessing properties");
            exerciseRepository.save(simpleClass);

            // Create topics for Python
            Topic pythonBasics = new Topic();
            pythonBasics.setName("Python Basics");
            pythonBasics.setDescription("Fundamentals of Python programming");
            pythonBasics.setLanguage(python);
            pythonBasics.setDifficultyLevel(1);
            pythonBasics.setOrderIndex(1);
            topicRepository.save(pythonBasics);

            // Create Python exercises
            Exercise pythonHello = new Exercise();
            pythonHello.setTitle("Python Hello World");
            pythonHello.setDescription("Print 'Hello, World!' in Python.");
            pythonHello.setTopic(pythonBasics);
            pythonHello.setDifficultyLevel(1);
            pythonHello.setStarterCode("# Write your code here");
            pythonHello.setSolutionCode("print('Hello, World!')");
            pythonHello.setTestCases("# Expected output: Hello, World!");
            exerciseRepository.save(pythonHello);
        }
    }
}
