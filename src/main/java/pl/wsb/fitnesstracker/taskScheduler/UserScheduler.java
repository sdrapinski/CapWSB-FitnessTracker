package pl.wsb.fitnesstracker.taskScheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.JavaEmailSender;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingRepository;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserProvider;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@EnableScheduling
@Component
@Slf4j
public class UserScheduler {

    TrainingRepository trainingRepository;
    UserProvider userProvider;
    JavaEmailSender mailSender;

    @Scheduled(cron = "0 0 1 * * MON")
    public void scheduleUserTrainings() {

        List<User> users = userProvider.findAllUsers();
        Date now = new Date();

        for (User user : users) {
            Long userId = user.getId();
            List<Training> trainings = trainingRepository.getTrainingsByUserId(userId);

            // get trainings from last week
            Date oneWeekAgo = new Date(now.getTime() - 7L * 24 * 60 * 60 * 1000);
            Stream<Training> weeklyTrainings = trainings.stream()
                    .filter(training -> training.getStartTime().after(oneWeekAgo));

            int weeklyCount = 0;

            String MailBody = "";

            System.out.println("User ID: {userId}, Trainings in the last week: {weeklyTrainings.count()}");
            MailBody =("User ID: {userId}, Trainings in the last week: {weeklyTrainings.count()}");

            for (Training training : weeklyTrainings.toList()) {
                weeklyCount += training.getDistance();
                System.out.println(" - Training ID: {training.getId()}, Start Time: {training.getStartTime()} End Time: {training.getEndTime()} Activity: {training.getActivityType()}");
                MailBody +=("\n - Training ID: {training.getId()}, Start Time: {training.getStartTime()} End Time: {training.getEndTime()} Activity: {training.getActivityType()}");

            }

            System.out.println("Total distance in the last week for User ID {userId}: {weeklyCount} km");
            MailBody+=("\nTotal distance in the last week for User ID {userId}: {weeklyCount} km");

            EmailDto email = new EmailDto(
                    user.getEmail(),
                "Your Weekly Training Summary",
                    MailBody,
                    "fitnesstracker@cap.wsb.com"
            );
            mailSender.send(email);

        }
    }
}
