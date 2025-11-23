# Java Symmetric Encryption (AES-GCM)

A simple Java implementation demonstrating symmetric encryption using AES (Advanced Encryption Standard) with GCM (Galois/Counter Mode) mode of operation.

## Overview

This project provides a working example of secure symmetric encryption and decryption in Java. It uses the AES algorithm with GCM mode, which provides both confidentiality and authenticity of data.

## Features

- **AES-128 Encryption**: Uses 128-bit AES encryption keys
- **GCM Mode**: Galois/Counter Mode provides authenticated encryption
- **Secure IV Generation**: Uses `SecureRandom` to generate initialization vectors
- **Base64 Encoding**: Encrypted data is encoded in Base64 for easy storage and transmission
- **Complete Example**: Includes both encryption and decryption in a single class

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- No external dependencies required (uses standard Java Cryptography Extension)

## How to Compile and Run

### Using Command Line

1. Navigate to the project directory:
```bash
cd Java-Symmetric-Encryption
```

2. Compile the Java file:
```bash
javac src/AESGCM.java
```

3. Run the program:
```bash
cd src
java AESGCM
```

### Expected Output

The program will encrypt the text "Asela" and then decrypt it, printing both the encrypted (Base64-encoded) text and the decrypted plaintext:

```
[Base64 encrypted string]
Asela
```

## Code Structure

### Main Components

- **`generateKey()`**: Generates a random 128-bit AES secret key
- **`encrypt(SecretKey key, byte[] data)`**: Encrypts data using AES-GCM
- **`decrypt(SecretKey key, String cipherText)`**: Decrypts Base64-encoded ciphertext

### Encryption Process

1. Generates a 12-byte initialization vector (IV) using `SecureRandom`
2. Configures AES cipher in GCM mode with 128-bit authentication tag
3. Encrypts the data
4. Prepends the IV to the ciphertext
5. Encodes the result in Base64

### Decryption Process

1. Decodes the Base64-encoded ciphertext
2. Extracts the IV from the first 12 bytes
3. Extracts the actual ciphertext from the remaining bytes
4. Configures the cipher for decryption
5. Decrypts and returns the plaintext

## Security Considerations

- **IV Uniqueness**: The implementation generates a new random IV for each encryption operation, which is critical for GCM mode security
- **Authentication Tag**: GCM mode includes a 128-bit authentication tag to detect tampering
- **Key Management**: In production environments, secret keys should be securely stored and managed (e.g., using key management systems)
- **IV Size**: Uses the NIST-recommended 12-byte IV size for GCM mode

## Usage Example

```java
// Generate a secret key
SecretKey secretKey = generateKey();

// Encrypt data
String encrypted = encrypt(secretKey, "Your message".getBytes(StandardCharsets.UTF_8));
System.out.println("Encrypted: " + encrypted);

// Decrypt data
String decrypted = decrypt(secretKey, encrypted);
System.out.println("Decrypted: " + decrypted);
```

## Important Notes

- The same secret key must be used for both encryption and decryption
- Each encryption operation generates a unique IV, so the same plaintext will produce different ciphertexts
- The encrypted output includes both the IV and the ciphertext, making it self-contained
- This is a demonstration project; for production use, consider additional security measures and proper key management

## License

This project is available for educational and demonstration purposes.
