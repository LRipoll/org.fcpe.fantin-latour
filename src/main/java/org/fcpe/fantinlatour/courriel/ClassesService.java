package org.fcpe.fantinlatour.courriel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.Classe;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class ClassesService extends AbstractVelocityService {

	private AnneeScolaire anneeScolaire;
	private JavaMailSender mailSender;

	public ClassesService(AnneeScolaire anneeScolaire) {
		super();
		this.anneeScolaire = anneeScolaire;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void run() throws FileNotFoundException, UnsupportedEncodingException {
		for (Classe classe : anneeScolaire.getClasses()) {

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("classe", classe);

			String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
					"velocity/lettre-relance-conseil-classe.vm", "UTF-8", model);
			PrintWriter writer = new PrintWriter(
					System.getProperty("user.home")+"/Documents/GitHub/org.fcpe.fantin-latour/target/exports/classe" + classe.getNom() + ".html",
					"UTF-8");
			writer.println(text);

			writer.close();

			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
					StringBuffer courriels = new StringBuffer();
					for (String courriel : classe.getCourrielsDesDelegues()) {
						if (courriels.length() > 0) {
							courriels.append(",");
						}
						courriels.append(courriel);
					}
					
					message.setTo((String[]) classe.getCourrielsDesDelegues().toArray(new
					String[0]));
					message.setBcc("adresse@operator.fr");
					message.setFrom(new InternetAddress("adresse@operator.fr"));
					message.setSubject(
							String.format("[fantinlatour][délégués][%s] : Informations concernant votre classe",
									classe.getNomComplet()));
					message.setSentDate(new Date());

					message.setText(text, true);

				}

			};
			mailSender.send(preparator);

		}

	}
}
