package com.ddb.userdirectory.service;

import com.ddb.userdirectory.dto.CreateUserRequest;
import com.ddb.userdirectory.dto.DatasetGenerationResponse;
import com.ddb.userdirectory.model.ShardName;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

@Service
public class DatasetGeneratorService {

    private final UserService userService;
    private final Random random = new Random();

    private static final String[] COUNTRIES = {
            "Vietnam", "USA", "Canada", "Japan", "Korea",
            "Germany", "France", "Australia", "Singapore", "Thailand"
    };

    public DatasetGeneratorService(UserService userService) {
        this.userService = userService;
    }

    public DatasetGenerationResponse generateUsers(int size, boolean clearedBeforeGenerate) {

        validateSize(size);

        if (clearedBeforeGenerate) {
            userService.clearAllUsers();
        }

        int skewedCount = (int) Math.round(size * 0.7);
        int otherCount = size - skewedCount;

        int countM = 0;
        int countS = 0;
        int countOther = 0;

        for (int i = 1; i <= skewedCount; i++) {
            boolean useM = random.nextBoolean();

            String username;

            if (useM) {
                username = buildUsername("M", i);
                countM++;
            } else {
                username = buildUsername("S", i);
                countS++;
            }

            CreateUserRequest request = new CreateUserRequest(
                    username,
                    username.toLowerCase() + "@example.com",
                    randomCountry());

            userService.createUser(request);
        }

        for (int i = 1; i <= otherCount; i++) {
            char firstLetter = randomOtherLetter();

            String username = buildUsername(String.valueOf(firstLetter), i);
            countOther++;

            CreateUserRequest request = new CreateUserRequest(
                    username,
                    username.toLowerCase() + "@example.com",
                    randomCountry());

            userService.createUser(request);
        }

        Map<String, Long> insertedPerShard = new LinkedHashMap<>();
        insertedPerShard.put("AG", userService.countUsersInShard(ShardName.AG));
        insertedPerShard.put("HN", userService.countUsersInShard(ShardName.HN));
        insertedPerShard.put("OZ", userService.countUsersInShard(ShardName.OZ));

        return new DatasetGenerationResponse(
                size,
                countM,
                countS,
                countOther,
                insertedPerShard,
                clearedBeforeGenerate,
                "Generated skew dataset successfully");
    }

    private void validateSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Kích thước dataset phải lớn hơn 0");
        }

        if (size > 100000) {
            throw new IllegalArgumentException("Kích thước dataset quá lớn cho môi trường demo local");
        }
    }

    private String buildUsername(String prefix, int index) {
        return prefix + "User" + String.format("%05d", index);
    }

    private String randomCountry() {
        return COUNTRIES[random.nextInt(COUNTRIES.length)];
    }

    private char randomOtherLetter() {
        String letters = "ABCDEFGHIJKLNOPQRTUVWXYZ";
        return letters.charAt(random.nextInt(letters.length()));
    }
}
