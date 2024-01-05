package common;

import java.io.Serializable;

/**
 * Ogni sua istanza rappresenta una persona e raccoglie i dati personali inseriti dall'utente.
 *
 * @author Tropeano Martina 749890 VA
 * @author Guenzani Pierpaolo 738675 VA
 */
public class UtenteRegistrato implements Serializable, Comparable<UtenteRegistrato>
{
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private String indirizzoFisico;
	private String email;
	private String password;
	private final String userId;

	/**
	 * Costruisce un nuovo utente registrato con i dati personali e le credenziali di accesso fornite.
	 *
	 * @param nome            Il nome della persona (non può essere vuoto).
	 * @param cognome         Il cognome della persona (non può essere vuoto).
	 * @param codiceFiscale   Il codice fiscale della persona (non può essere vuoto).
	 * @param indirizzoFisico L'indirizzo fisico della persona (non può essere vuoto).
	 * @param email           L'indirizzo e-mail della persona (non può essere vuoto).
	 * @param password        La password di accesso all'applicazione (non può essere vuota).
	 * @param userId          Il nome utente di accesso all'applicazione (non può essere vuoto).
	 * @throws NullPointerException Se uno dei parametri obbligatori è vuoto.
	 */
	public UtenteRegistrato(String nome, String cognome, String codiceFiscale, String indirizzoFisico, String email, String password, String userId)
	{
		if(nome.equals("")) throw new NullPointerException("Il campo Nome non può essere vuoto.");
		if(cognome.equals("")) throw new NullPointerException("Il campo Cognome non può essere vuoto.");
		if(codiceFiscale.equals("")) throw new NullPointerException("Il campo Codice Fiscale non può essere vuoto.");
		if(indirizzoFisico.equals("")) throw new NullPointerException("Il campo Indirizzo Fisico non può essere vuoto.");
		if(email.equals("")) throw new NullPointerException("Il campo Email non può essere vuoto.");
		if(password.equals("")) throw new NullPointerException("Il campo Password non può essere vuoto.");
		if(userId.equals("")) throw new NullPointerException("Il campo UserID non può essere vuoto.");

		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.indirizzoFisico = indirizzoFisico;
		this.email = email;
		this.password = password;
		this.userId = userId;
	}

	/**
	 * Restituisce il nome della persona rappresentata dall'oggetto.
	 *
	 * @return il nome della persona
	 */
	public String getNome()
	{
		return nome;
	}

	/**
	 * Restituisce il cognome della persona rappresentata dall'oggetto.
	 *
	 * @return il cognome della persona
	 */
	public String getCognome()
	{
		return cognome;
	}

	/**
	 * Restituisce il codice fiscale della persona rappresentata dall'oggetto.
	 *
	 * @return il codice fiscale della persona
	 */
	public String getCodiceFiscale()
	{
		return codiceFiscale;
	}

	/**
	 * Restituisce l'indirizzo fisico della persona rappresentata dall'oggetto.
	 *
	 * @return l'indirizzo fisico della persona
	 */
	public String getIndirizzoFisico()
	{
		return indirizzoFisico;
	}

	/**
	 * Restituisce l'indirizzo email della persona rappresentata dall'oggetto.
	 *
	 * @return l'indirizzo email della persona
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * Restituisce la password di accesso all'account della persona rappresentata dall'oggetto.
	 *
	 * @return la password di accesso della persona
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * Restituisce il nome utente della persona rappresentata dall'oggetto.
	 *
	 * @return il nome utente della persona
	 */
	public String getUserId(){
		return userId;
	}
	

	/**
	 * Assegna il nome fornito come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, il nuovo nome viene sovrascritto al precedente.
	 *
	 * @param nuovoNome il nome da assegnare alla persona
	 */
	public void setNome(String nuovoNome)
	{
		if(nuovoNome.equals("")) throw new NullPointerException("Il campo Nome non può essere vuoto.");
		this.nome = nuovoNome;
	}

	/**
	 * Assegna il cognome fornito come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, il nuovo cognome viene sovrascritto al precedente.
	 *
	 * @param nuovoCognome il cognome da assegnare alla persona
	 */
	public void setCognome(String nuovoCognome)
	{
		if(nuovoCognome.equals("")) throw new NullPointerException("Il campo Cognome non può essere vuoto.");
		this.cognome = nuovoCognome;
	}

	/**
	 * Assegna il codice fiscale fornito come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, il nuovo codice fiscale viene sovrascritto al precedente.
	 *
	 * @param nuovoCodiceFiscale il codice fiscale da assegnare alla persona
	 */
	public void setCodiceFiscale(String nuovoCodiceFiscale)
	{
		if(nuovoCodiceFiscale.equals("")) throw new NullPointerException("Il campo Codice Fiscale non può essere vuoto.");
		this.codiceFiscale = nuovoCodiceFiscale;
	}

	/**
	 * Assegna l'indirizzo fornito come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, il nuovo indirizzo viene sovrascritto al precedente.
	 *
	 * @param nuovoIndirizzoFisico l'indirizzo da assegnare alla persona
	 */
	public void setIndirizzoFisico(String nuovoIndirizzoFisico)
	{
		if(nuovoIndirizzoFisico.equals("")) throw new NullPointerException("Il campo Indirizzo Fisico non può essere vuoto.");
		this.indirizzoFisico = nuovoIndirizzoFisico;
	}

	/**
	 * Assegna l'indirizzo email fornito come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, il nuovo indirizzo email viene sovrascritto al precedente.
	 *
	 * @param nuovaEmail l'indirizzo email da assegnare alla persona
	 */
	public void setEmail(String nuovaEmail)
	{
		if(nuovaEmail.equals("")) throw new NullPointerException("Il campo Email non può essere vuoto.");
		this.email = nuovaEmail;
	}

	/**
	 * Assegna la password fornita come argomento alla persona rappresentata dall'oggetto.
	 * Nel caso in cui ne sia gia' dotata, la nuova password viene sovrascritta alla precedente.
	 *
	 * @param nuovaPassword la password da assegnare alla persona
	 */
	public void setPassword(String nuovaPassword)
	{
		if(nuovaPassword.equals("")) throw new NullPointerException("Il campo Password non può essere vuoto.");
		this.password = nuovaPassword;
	}

	/**
	 * Confronta la persona fornita come argomento con quella rappresentata dall'oggetto in base al loro codice fiscale.
	 *
	 * @param utenteRegistrato L'oggetto UtenteRegistrato con cui effettuare il confronto.
	 * @return Un valore negativo se l'oggetto corrente è "minore" dell'oggetto fornito,
	 *         zero se sono "uguali" e un valore positivo se l'oggetto corrente è "maggiore".
	 * @throws IllegalArgumentException Se l'oggetto fornito come argomento è null.
	 * @throws ClassCastException Se il tipo dell'oggetto fornito come argomento non consente il confronto.
	 * @see String#compareTo(String)
	 */
	@Override
	public int compareTo(UtenteRegistrato utenteRegistrato)
	{
		if (utenteRegistrato == null) throw new IllegalArgumentException("Comparazione fallita.");
		return codiceFiscale.compareTo(utenteRegistrato.codiceFiscale);
	}
 
}
