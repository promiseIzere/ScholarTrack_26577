package com.scholartrack.service;

import com.scholartrack.model.Student;
import com.scholartrack.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CSVService {

    private final StudentRepository studentRepository;

    public CSVService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public List<Student> importStudentsCsv(MultipartFile file) throws IOException {
        List<Student> imported = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean headerSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) { headerSkipped = true; continue; }
                String[] parts = line.split(",");
                if (parts.length < 3) continue;
                Student s = new Student();
                s.setFirstName(parts[0].trim());
                s.setLastName(parts[1].trim());
                s.setEmail(parts[2].trim());
                // Note: parentEmail field not available in current Student model
                // if (parts.length >= 4) s.setParentEmail(parts[3].trim());
                imported.add(studentRepository.save(s));
            }
        }
        return imported;
    }
}


