package yool1.ma.authservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import yool1.ma.authservice.Enum.Niveau;
import yool1.ma.authservice.Enum.Ville;
import yool1.ma.authservice.entities.*;
import yool1.ma.authservice.repository.*;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {ConfigurableApplicationContext context = SpringApplication.run(AuthServiceApplication.class, args);}

    @Bean
    CommandLineRunner start(UserRepository userRepository,ApprenantRepository apprenantRepository,ProfileRepository profileRepository) {
        return args -> {


            // Create and save user and profile in one transaction
            User user = User.builder()
                    .firstName("Oumy")
                    .lastName("Mer")
                    .email("oumy@gml.com")
                    .phone("000111222")
                    .ville(Ville.Agadir)
                    .dateInscription(LocalDate.now())
                    .motDePasse("123456")
                    .role("APPRENANT")
                    .build();


            Profile profile = Profile.builder()
                    .headline("Apprenant profile")
                    .bio("o.s.web.servlet.DispatcherServlet ")
                    .location("Lot N 150  agadir")
                    .photoUrl("http://localhost:8021/img/logo.png") // Add default photo
                    .build();

            // Set the bidirectional relationship
            profile.setUser(user);
            user.setProfile(profile); // Add this to your User entity

            // Save just the profile - user will be saved automatically due to cascade
            profileRepository.save(profile);




            // Création et sauvegarde d'un Apprenant
            Apprenant apprenant = new Apprenant();
            apprenant.setFirstName("Jean");
            apprenant.setLastName("Dupont");
            apprenant.setEmail("jean.dupont@example.com");
            apprenant.setPhone("0612345678");
            apprenant.setVille(Ville.Agadir);

            apprenant.setDateInscription(LocalDate.now());
            apprenant.setMotDePasse("password123");
            apprenant.setRole("APPRENANT");
            apprenant.setNiveau(Niveau.AVANCE);
            apprenant.setFormation("Informatique");
            apprenant.setDateDebut(new Date());
            apprenantRepository.save(apprenant);

            System.out.println("Exemples d'utilisateurs créés avec succès !");
        };
    }
}



// Création et sauvegarde d'un Lauréat
//            Laureat laureat = new Laureat();
//            laureat.setFirstName("Marie");
//            laureat.setLastName("Martin");
//            laureat.setEmail("marie.martin@example.com");
//            laureat.setPhone("0698765432");
//            laureat.setVille(Ville.Casablanca);
//
//            laureat.setDateInscription(LocalDate.now().minusYears(1));
//            laureat.setMotDePasse("password456");
//            laureat.setRole("LAUREAT");
//            laureat.setDateObtentionDiplome(new Date());
//            laureat.setSpecialite("Machine Learning");
//            laureatRepository.save(laureat);

// Création et sauvegarde d'un Recruteur
//            Recruteur recruteur = new Recruteur();
//            recruteur.setFirstName("Pierre");
//            recruteur.setLastName("Durand");
//            recruteur.setEmail("pierre.durand@entreprise.com");
//            recruteur.setPhone("0678912345");
//            recruteur.setVille(Ville.Agadir);
//
//            recruteur.setDateInscription(LocalDate.now());
//            recruteur.setMotDePasse("recruteurPass");
//            recruteur.setRole("RECRUTEUR");
//            recruteur.setEntreprise("TechCorp");
//            recruteur.setSecteur("Informatique");
//            recruteurRepository.save(recruteur);

// Création et sauvegarde d'un Responsable
//            Responsable responsable = new Responsable();
//            responsable.setFirstName("Sophie");
//            responsable.setLastName("Leroy");
//            responsable.setEmail("sophie.leroy@ecole.com");
//            responsable.setPhone("0654321890");
//            responsable.setVille(Ville.Agadir);
//
//            responsable.setDateInscription(LocalDate.now().minusMonths(3));
//            responsable.setMotDePasse("adminPass");
//            responsable.setRole("RESPONSABLE");
//            responsable.setFonction("Directrice pédagogique");
//            responsable.setDepartement("Informatique");
//            responsableRepository.save(responsable);