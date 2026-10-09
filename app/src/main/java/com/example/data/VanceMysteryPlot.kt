package com.example.data

import com.example.model.CauseOfDeathProfile
import com.example.model.CrimeSceneClue
import com.example.model.EvidenceCategory
import com.example.model.InterrogationQuestion
import com.example.model.MurdererProfile
import com.example.model.ObjectiveItem
import com.example.model.PlotTwist
import com.example.model.SuspectCharacter
import com.example.model.TimelineEvent
import com.example.model.VictimProfile

object VanceMysteryPlot {

  val victim = VictimProfile(
    name = "Lord Alistair Vance",
    age = 64,
    title = "Eccentric Botanical Tycoon & Patriarch",
    background = "Founder of Vance Flora Pharmaceuticals, worth $40 million. He invited his inner circle to his estate tonight under the guise of an autumn dinner, but secretly summoned his notary to rewrite his will and cut everyone out at midnight.",
    foundLocation = "The Stone Gazebo / Backyard Arbor (Or Home Patio/Garden)",
    physicalAppearance = "Dressed in an emerald silk smoking jacket. Found slumped over the stone birdbath, fingers clawed into the soil, pupils intensely dilated. A shattered crystal port wine glass lay beside him.",
    lastKnownWords = "\"Before the grandfather clock strikes twelve, the dead wood in this family shall be pruned to the root. None of you will leech off my soil any longer.\""
  )

  val causeOfDeath = CauseOfDeathProfile(
    primaryCause = "Acute Neurotoxic Cardiac Arrest from Concentrated Aconite (Monkshood / Wolfsbane)",
    actualWeaponOrToxin = "5 drops of purified botanical aconite neurotoxin dissolved in his private decanter of 1982 Graham's Vintage Port.",
    stagedSecondaryWeapon = "Heavy bronze fireplace poker blow to the right temple, staged against the sharp edge of the stone birdbath to simulate a drunken stumble.",
    coronerFindings = "Coroner's inquest reveals no subdural hematoma or arterial bleeding from the skull impact. The heart was already in severe ventricular fibrillation before the impact occurred. The head wound was inflicted post-mortem or while paralyzed.",
    toxicologyReport = "Mass spectrometry confirms lethal concentration of Aconitine in gastric contents and bloodstream. Ingestion window: between 7:40 PM and 7:55 PM. Death occurred at approximately 8:10 PM."
  )

  val murderer = MurdererProfile(
    characterId = "dr_ward",
    characterName = "Dr. Julian Ward",
    fullMotive = "For the past three years, Dr. Ward siphoned $3.2 million from the Vance Medical Research Trust to settle offshore derivative debts. To maintain control, he falsified Alistair's medical charts to convince him he had terminal coronary disease. Yesterday afternoon, Alistair's private forensic auditor uncovered the missing funds. Alistair summoned Ward to the study at 7:15 PM and threatened Interpol arrest and total ruin tonight.",
    premeditationDetails = "Knowing Alistair always secluded himself on the patio with his private 1982 Port decanter after dessert, Ward carried a sterile pipette of concentrated aconitine extract from his clinic bag and spiked the decanter while Alistair argued with Elena.",
    fatalMistake = "Dr. Ward dropped his custom gold monogrammed fountain pen (\"J.W.\") behind the patio drinks trolley, and aconite chemical residue remains on the inside cuff of his left jacket sleeve.",
    confessionStatement = "\"Alistair was a tyrant who built an empire on crushed bones. He treated me like a hired servant while I kept his rotten heart beating for decades. He wanted to destroy my life, my name, my hospital. He forced my hand!\""
  )

  val timelineEvents = listOf(
    TimelineEvent(
      time = "7:00 PM",
      title = "The Opening Reception & Toast",
      description = "Guests arrive on the patio/living room. Lord Vance raises his private port decanter, insults every guest in attendance, and announces his entire fortune is being transferred to a botanical preservation society at midnight.",
      involvedCharacters = listOf("Lord Vance", "Elena Vance", "Dr. Ward", "Vivienne Delacroix", "Marcus Sterling"),
      location = "Patio / Living Room"
    ),
    TimelineEvent(
      time = "7:35 PM",
      title = "The Violent Argument in the Conservatory",
      description = "A shouting match erupts between Alistair and Elena. Sounds of a vase shattering. Elena storms out toward the back terrace. During the commotion, Dr. Ward lingers suspiciously by the drinks bar.",
      involvedCharacters = listOf("Lord Vance", "Elena Vance", "Dr. Ward"),
      location = "Conservatory / Hallway"
    ),
    TimelineEvent(
      time = "8:00 PM",
      title = "The Estate Blackout",
      description = "Lightning flashes outside; the estate main circuit breaker trips and plunges the house and backyard into pitch black darkness for 4 full minutes. Shuffling footsteps and muffled voices are heard in the corridors.",
      involvedCharacters = listOf("All Guests"),
      location = "Main House & Grounds"
    ),
    TimelineEvent(
      time = "8:12 PM",
      title = "The Dull Thud in the Darkness",
      description = "A heavy, muffled thud echoes from the stone gazebo. Dr. Ward returns to the dining room carrying a lit taper candle, looking pale and out of breath.",
      involvedCharacters = listOf("Dr. Ward", "Vivienne Delacroix"),
      location = "Garden Gazebo / Dining Room"
    ),
    TimelineEvent(
      time = "8:30 PM",
      title = "Discovery of the Body",
      description = "Vivienne Delacroix walks out to the gazebo to bring Alistair his jacket and screams. Lord Vance is discovered slumped motionless against the stone birdbath, blood pooling on the flagstones.",
      involvedCharacters = listOf("Lord Vance", "Vivienne Delacroix", "Marcus Sterling"),
      location = "Stone Gazebo / Backyard Patio"
    )
  )

  val plotTwists = listOf(
    PlotTwist(
      number = 1,
      title = "The Double Poisoning (Someone Beat Them To It!)",
      summary = "Two different guests attempted to murder Alistair Vance on the exact same night!",
      howPlayersDiscoverIt = "By examining Vivienne Delacroix's vintage silver makeup compact, detectives find a hidden compartment containing white arsenic powder. However, toxicology proves Alistair died of aconite neurotoxin, not arsenic! Vivienne intended to slip arsenic into his nightcap tea, but discovered he was already poisoned when she approached him at 8:25 PM.",
      narrativeImpact = "Flips the investigation completely on its head—just because a suspect is proven to have poison doesn't make them the killer! Players must distinguish between intent and execution."
    ),
    PlotTwist(
      number = 2,
      title = "The Staged Blunt Force Trauma",
      summary = "The bloody head wound on the birdbath was completely staged after death to fake an accident.",
      howPlayersDiscoverIt = "The Coroner's initial report noted a severe skull fracture, but the forensic reagent test shows zero arterial bleeding. Dr. Ward entered the gazebo during the blackout, found Alistair paralyzed and dying from the aconite, and struck his head against the stone with a bronze fire poker to make it look like he stumbled drunkenly in the dark.",
      narrativeImpact = "Destroys the physical weapon theory. The fire poker covered in blood is a red herring; the true lethal instrument was chemical."
    ),
    PlotTwist(
      number = 3,
      title = "The Victim's Final Dying Message",
      summary = "Lord Vance knew his killer was coming and left an encrypted dying clue in his own handwriting.",
      howPlayersDiscoverIt = "A silk handkerchief recovered from Vance's clenched fist contains faint chemical writing. Under the UV light/decoder mini-game, it reveals: 'J.W. ... Port ... Check Shed Safe.' Alistair used his botanical testing pen to write his doctor's initials before his lungs seized.",
      narrativeImpact = "Provides unshakeable concrete proof tying Dr. Julian Ward to the crime and pointing detectives to the embezzled foundation audit in the shed."
    )
  )

  val characters = listOf(
    SuspectCharacter(
      id = "dr_ward",
      name = "Dr. Julian Ward",
      alias = "The Family Physician",
      age = 58,
      roleTitle = "Renowned Cardiologist & Alistair's Lifelong Friend",
      attireSuggestion = "Charcoal tailored suit, silver pocket watch, doctor's leather valise or lapel pin.",
      publicBio = "A distinguished society physician who has looked after Alistair's fragile health for over thirty years. Always calm, dignified, and soft-spoken.",
      relationshipToVictim = "Lifelong confidant, medical advisor, and co-trustee of the Vance Botanical Research Endowment.",
      secretDarkMotive = "YOU ARE THE MURDERER! You embezzled $3.2 Million from Alistair's research foundation to cover illicit financial trades. Alistair found out yesterday and confronted you in private, vowing to call the Chief of Police tonight at 9:00 PM. You poisoned his Port decanter with 5 drops of concentrated aconite. When the lights went out, you checked on him, found him expiring, and smashed his head against the birdbath with a poker to stage an accidental fall.",
      whatTheyHide = "Your monogrammed gold fountain pen dropped near the drinks trolley. Aconite chemical residue on your jacket cuff. Your fake medical ledger is hidden in Marcus's shed.",
      alibiStatement = "\"I was in the drawing room reading the Medical Lancet from 7:50 PM until the power went out. When the blackout hit, I immediately went to the pantry with Vivienne to locate emergency candles.\"",
      alibiFlaw = "The butler notes the drawing room was empty at 8:05 PM. Vivienne confirms Dr. Ward was missing for at least 8 minutes during the blackout, only appearing in the hallway after the dull thud was heard.",
      initialObjectives = listOf(
        ObjectiveItem("ward_1", "Direct suspicion toward Elena's screaming match and her financial desperation.", false),
        ObjectiveItem("ward_2", "Quietly find out if anyone has entered the drinks trolley or noticed the missing port glass.", false),
        ObjectiveItem("ward_3", "Convince guests that Alistair's head wound indicates a drunken fall in the pitch dark.", false),
        ObjectiveItem("ward_4", "Keep your left sleeve rolled down so no one notices the chemical staining.", false)
      ),
      gossipKnown = listOf(
        "Elena Vance was threatened with eviction yesterday by a private loan shark.",
        "Marcus was caught smuggling rare South American orchids out of the greenhouse last Tuesday.",
        "Vivienne demanded a $10 million pre-nuptial modification earlier this afternoon."
      ),
      interrogationQuestions = listOf(
        InterrogationQuestion(
          id = "ward_q1",
          questionPrompt = "Why does the butler state the drawing room was completely empty at 8:05 PM?",
          suspectResponse = "\"I stepped into the corridor for fresh air! Alistair's house was stifling hot before the storm blew the fuse.\"",
          bodyLanguageTell = "Adjusts spectacles nervously and avoids eye contact.",
          revealsClueHint = "His left jacket sleeve has a dark chemical stain tucked under the cuff."
        ),
        InterrogationQuestion(
          id = "ward_q2",
          questionPrompt = "We found your gold monogrammed pen 'J.W.' dropped behind the port trolley. Why were you near his private decanter?",
          suspectResponse = "\"I was prescribing Alistair heart digitalis earlier in the evening! Writing prescriptions is my profession.\"",
          bodyLanguageTell = "Swallows hard; hand begins to tremble slightly.",
          revealsClueHint = "Digitalis does not require an eyedropper. Aconite does."
        ),
        InterrogationQuestion(
          id = "ward_q3",
          questionPrompt = "What was Alistair shouting at you about in his private study at 7:15 PM?",
          suspectResponse = "\"Merely research grants... Alistair was always prone to theatrical outbursts before dinner.\"",
          bodyLanguageTell = "Pales noticeably and quickly changes the subject.",
          revealsClueHint = "Check the audit folder hidden in Marcus's potting shed."
        )
      ),
      suspicionRating = 4,
      assignedGuestName = "Guest 1",
      guestPasscode = "1001"
    ),

    SuspectCharacter(
      id = "elena_vance",
      name = "Elena Vance",
      alias = "The Estranged Heiress",
      age = 32,
      roleTitle = "Art Gallery Curator & Alistair's Disowned Daughter",
      attireSuggestion = "Elegant black evening gown, bold crimson lipstick, statement pearl necklace.",
      publicBio = "Brilliant, fierce, and fiercely independent. Elena hasn't stepped foot in Vance Manor for three years following an explosive falling out with her father over her mother's inheritance.",
      relationshipToVictim = "Only biological daughter and legally next of kin.",
      secretDarkMotive = "You owe $850,000 to an illegal high-stakes casino syndicate who gave you until midnight tonight to pay. Tonight, you broke into your father's study safe to steal bearer bonds and replace the original will with a forged copy leaving everything to you! You had the motive and the opportunity.",
      whatTheyHide = "You have a pair of velvet lock-picking gloves and a forged draft of Lord Vance's will hidden inside your evening clutch purse.",
      alibiStatement = "\"I was out on the terrace garden taking a crucial 25-minute business call with my art dealer in Milan from 8:00 PM to 8:25 PM. I never went near the gazebo.\"",
      alibiFlaw = "Phone logs show the Milan call was disconnected at 8:06 PM due to estate storm interference. What were you doing in the dark for the remaining 19 minutes? (You were breaking into the study safe!)",
      initialObjectives = listOf(
        ObjectiveItem("elena_1", "Prevent anyone from searching the antique desk in the study.", false),
        ObjectiveItem("elena_2", "Question Vivienne about the pre-nuptial agreement she pressured Alistair to sign.", false),
        ObjectiveItem("elena_3", "Deny that the loud shouting match at 7:35 PM was about your debts.", false),
        ObjectiveItem("elena_4", "Find an ally who will corroborate that you were on the terrace during the blackout.", false)
      ),
      gossipKnown = listOf(
        "Father had a top-secret lockbox installed in the potting shed behind Marcus's toolbench.",
        "Dr. Ward looked like a walking ghost when he stepped out of Father's study at 7:25 PM.",
        "Vivienne has a degree in pharmaceutical biochemistry from Sorbonne that she hides from everyone."
      ),
      interrogationQuestions = listOf(
        InterrogationQuestion(
          id = "elena_q1",
          questionPrompt = "Cell carrier logs show your Milan call dropped at 8:06 PM. Where were you for the next 19 minutes in the dark?",
          suspectResponse = "\"I was wandering the back terrace in absolute fury! My father was threatening to disinherit me and humiliate me publicly.\"",
          bodyLanguageTell = "Clutches her evening purse tightly against her chest.",
          revealsClueHint = "Her purse contains lockpicks and a blue gel pen matching the forged will."
        ),
        InterrogationQuestion(
          id = "elena_q2",
          questionPrompt = "Did you threaten to kill your father during the 7:35 PM shouting match in the conservatory?",
          suspectResponse = "\"I told him he would rot in hell! That is family frustration, not premeditated homicide!\"",
          bodyLanguageTell = "Eyes flash with fierce indignation; jaw tightens.",
          revealsClueHint = "Elena wanted his money alive or dead, but had no medical access to poisons."
        )
      ),
      suspicionRating = 5,
      assignedGuestName = "Guest 2",
      guestPasscode = "2002"
    ),

    SuspectCharacter(
      id = "marcus_sterling",
      name = "Marcus 'Fox' Sterling",
      alias = "The Grounds-keeper",
      age = 46,
      roleTitle = "Head Horticulturalist & Estate Caretaker",
      attireSuggestion = "Tweed waistcoat, earth-toned button shirt, rugged leather boots, gardener's pocket knife.",
      publicBio = "A quiet, weathered man with deep knowledge of poisonous flora and estate grounds. Has worked for Lord Vance for 12 years and lives in the cottage by the back arbor.",
      relationshipToVictim = "Employee and keeper of Alistair's private botanical greenhouse.",
      secretDarkMotive = "Alistair caught you running an underground black market ring selling rare endangered orchid clones and deadly botanical extracts. Alistair called you into the conservatory and told you the police would be waiting for you on Monday morning. Furthermore, your fingerprints are on the greenhouse aconite bottle because you harvested the plant!",
      whatTheyHide = "You entered the gazebo at 8:18 PM in the dark, found Alistair dead, panicked, and stole his private leather ledger to destroy evidence of your plant sales. You have his stolen ledger in your boot!",
      alibiStatement = "\"I was out by the generator shed resetting the circuit breakers from 8:00 PM until the power surged back at 8:22 PM. I never went anywhere near the main house.\"",
      alibiFlaw = "A fresh muddy bootprint matching your size 11 tread was discovered right next to the gazebo birdbath where Alistair lay dead. Why are your boots covered in garden peat?",
      initialObjectives = listOf(
        ObjectiveItem("marcus_1", "Hide Lord Vance's stolen ledger somewhere secure or destroy the incriminating pages.", false),
        ObjectiveItem("marcus_2", "Explain away your bootprints by claiming you checked the arbor lights earlier at 6:30 PM.", false),
        ObjectiveItem("marcus_3", "Expose that Dr. Ward requested a key to the greenhouse poison cabinet three days ago.", false),
        ObjectiveItem("marcus_4", "Find out who tripped the generator breaker—it was manually turned off, not blown by storm!", false)
      ),
      gossipKnown = listOf(
        "The greenhouse poison cabinet lock had fresh brass scratch marks on it.",
        "Dr. Ward asked me last week what the fatal dosage of Monkshood sap would be for an adult heart.",
        "Elena Vance was rummaging through the study drawers right after dinner."
      ),
      interrogationQuestions = listOf(
        InterrogationQuestion(
          id = "marcus_q1",
          questionPrompt = "Your muddy size-11 bootprints were found 12 inches from Lord Vance's corpse. Explain that!",
          suspectResponse = "\"I... I found him! The power went out, I heard a gasp, ran to the arbor, and saw him dead. I panicked because I knew you'd blame me, so I ran!\"",
          bodyLanguageTell = "Shifts weight nervously; rubs calloused knuckles.",
          revealsClueHint = "He didn't kill him—he found him already expired and stole back his ledger."
        ),
        InterrogationQuestion(
          id = "marcus_q2",
          questionPrompt = "Who else has keys to your rare botanical poisons cabinet?",
          suspectResponse = "\"Dr. Ward requested my spare brass key on Tuesday! He claimed he needed botanical specimens for heart research.\"",
          bodyLanguageTell = "Leans forward eagerly, eager to redirect suspicion.",
          revealsClueHint = "Dr. Ward obtained the raw aconite directly from Marcus's greenhouse."
        )
      ),
      suspicionRating = 4,
      assignedGuestName = "Guest 3",
      guestPasscode = "3003"
    ),

    SuspectCharacter(
      id = "vivienne_delacroix",
      name = "Vivienne Delacroix",
      alias = "The Glamorous Fiancee",
      age = 29,
      roleTitle = "Parisian Socialite & Alistair's Bride-to-Be",
      attireSuggestion = "Slinky silk cocktail dress, faux fur stole, glittering rhinestone earrings, ornate compact mirror.",
      publicBio = "Engaged to Lord Vance for four months. Known for her razor-sharp wit, couture fashion, and opulent lifestyle. Frequently clashed with Elena over estate redecorating.",
      relationshipToVictim = "Fiancée set to marry Lord Vance in three weeks.",
      secretDarkMotive = "Alistair found out yesterday you were a bankrupt former chemistry student who targeted him for his fortune. At 7:00 PM, he privately told you the wedding was cancelled and you were leaving empty-handed tonight. In sheer desperation, you brought white arsenic powder in your compact mirror to poison his evening chamomile tea!",
      whatTheyHide = "A silver powder compact containing lethal arsenic powder hidden behind the face mirror.",
      alibiStatement = "\"I was in the guest powder room touching up my makeup and taking aspirin for a migraine from 8:05 PM until 8:25 PM. When I stepped out, Dr. Ward told me Alistair hadn't returned from the arbor.\"",
      alibiFlaw = "The guest powder room window faces the arbor. A guest saw someone in a shimmering dress slip out the patio door at 8:15 PM into the rain. You actually went to poison his tea, but found him already dead at the arbor and fled in terror!",
      initialObjectives = listOf(
        ObjectiveItem("vivienne_1", "Dispose of the arsenic powder in your compact before the forensic search begins.", false),
        ObjectiveItem("vivienne_2", "Shift focus onto Elena—highlight how much Elena hated her father.", false),
        ObjectiveItem("vivienne_3", "Cast doubt on Marcus's access to all estate botanical toxins.", false),
        ObjectiveItem("vivienne_4", "Pretend to be overwhelmed with grief whenever someone asks difficult questions.", false)
      ),
      gossipKnown = listOf(
        "Alistair had a secret cassette recorder in his smoking jacket that he used during important meetings.",
        "Elena offered the butler a bribe last week to make a wax impression of the safe key.",
        "Dr. Ward has heavy gambling debts registered under an alias at the Mayfair Club."
      ),
      interrogationQuestions = listOf(
        InterrogationQuestion(
          id = "vivienne_q1",
          questionPrompt = "We found fine white arsenic powder in your silver compact mirror. Why are you carrying lethal poison?",
          suspectResponse = "\"It's... it's French cosmetic talc! Many European brands use mineral compounds!\"",
          bodyLanguageTell = "Voice quavers; dabs at fake tears with an embroidered napkin.",
          revealsClueHint = "She planned to poison him, but the toxicology proves Alistair died of aconite, not arsenic."
        ),
        InterrogationQuestion(
          id = "vivienne_q2",
          questionPrompt = "A witness saw you in the rain near the arbor at 8:15 PM. What did you see?",
          suspectResponse = "\"I went out to talk to him... but when I stepped onto the terrace, I saw someone in a dark suit standing over him with a fireplace poker! I ran!\"",
          bodyLanguageTell = "Eyes widen with genuine terror; hands tremble violently.",
          revealsClueHint = "She witnessed Dr. Julian Ward staging the head wound post-mortem."
        )
      ),
      suspicionRating = 5,
      assignedGuestName = "Guest 4",
      guestPasscode = "4004"
    ),

    SuspectCharacter(
      id = "arthur_pendleton",
      name = "Arthur Pendleton",
      alias = "The Long-Serving Butler",
      age = 62,
      roleTitle = "Major-Domo & Vance Manor Steward for 30 Years",
      attireSuggestion = "Black tailcoat or vest, white gloves, silver tray, pocket notebook and pocket watch.",
      publicBio = "The discreet, observant bedrock of the manor. Knows every secret passage, locked drawer, and family scandal, but maintains impenetrable professional decorum.",
      relationshipToVictim = "Trusted household butler and executor of the estate's physical keys.",
      secretDarkMotive = "Lord Vance recently accused you of stealing antique silverware and informed you that after 30 years of loyal service, you were being terminated without pension at the end of the month.",
      whatTheyHide = "A master key ring that unlocks the study safe, the wine cellar, and the greenhouse poison cabinet.",
      alibiStatement = "\"I was in the downstairs kitchen preparing the dessert soufflé and polishing the silver service during the entire blackout period.\"",
      alibiFlaw = "The kitchen clock stopped at 8:02 PM. The maid testified that the kitchen was pitch black and empty when she searched for candles.",
      initialObjectives = listOf(
        ObjectiveItem("arthur_1", "Observe all guests and discretely note who enters the crime scene area.", false),
        ObjectiveItem("arthur_2", "Protect your master key ring from being inspected by the amateur sleuths.", false),
        ObjectiveItem("arthur_3", "Deliver the Coroner's update to the guests when instructed by the host.", false)
      ),
      gossipKnown = listOf(
        "Lord Vance drank exclusively from his personal decanter—no one else was allowed to touch it.",
        "Dr. Ward asked me where the spare Port glasses were kept at 7:30 PM.",
        "Miss Elena was arguing fiercely on the terrace shortly before the thunder struck."
      ),
      interrogationQuestions = listOf(
        InterrogationQuestion(
          id = "arthur_q1",
          questionPrompt = "Who poured Lord Vance's port wine this evening?",
          suspectResponse = "\"His Lordship always poured his own vintage Graham's 1982 Port from the silver decanter. However, Dr. Ward was hovering over the drinks trolley while Elena was shouting.\"",
          bodyLanguageTell = "Maintains rigid posture; gestures subtly toward the bar cart.",
          revealsClueHint = "Dr. Ward had sole unsupervised access to the decanter at 7:38 PM."
        )
      ),
      suspicionRating = 3,
      assignedGuestName = "Guest 5",
      guestPasscode = "5005"
    ),

    SuspectCharacter(
      id = "beatrice_cross",
      name = "Beatrice Cross",
      alias = "The Investigative Biographer",
      age = 39,
      roleTitle = "True-Crime Author & Alistair's Authorized Biographer",
      attireSuggestion = "Vintage trench coat, tortoiseshell glasses, reporter notebook, audio dictaphone.",
      publicBio = "Commissioned six months ago to write the definitive biography of Lord Alistair Vance. Sharp, inquisitive, and always taking notes on guests' micro-expressions.",
      relationshipToVictim = "Biographer with unfettered access to family archives.",
      secretDarkMotive = "You discovered that Alistair Vance stole the patent for his multi-million dollar pharmaceutical formula from your late father 35 years ago, driving your father to financial ruin and suicide. You came here tonight to record his on-tape confession or ruin him.",
      whatTheyHide = "An audio recording cassette containing Alistair laughing about destroying your father's business.",
      alibiStatement = "\"I was in the library transcribing interview notes by oil lamp when the power failed. I never left the room until the screams began.\"",
      alibiFlaw = "A muddy footprint outside the library French doors indicates someone climbed out onto the veranda during the storm.",
      initialObjectives = listOf(
        ObjectiveItem("beatrice_1", "Interview Dr. Ward about Alistair's true medical condition.", false),
        ObjectiveItem("beatrice_2", "Search for Lord Vance's dictaphone or micro-cassette recorder on the grounds.", false),
        ObjectiveItem("beatrice_3", "Examine the documents in the study safe for patent records.", false)
      ),
      gossipKnown = listOf(
        "Lord Vance had handwritten notes about 'Dr. J.W. Embezzlement' inside his leather desk blotter.",
        "Vivienne Delacroix had her credit cards frozen by three Parisian banks this morning.",
        "The generator breaker was intentionally switched off with a wrench."
      ),
      interrogationQuestions = listOf(
        InterrogationQuestion(
          id = "beatrice_q1",
          questionPrompt = "What was the last note you wrote in your diary before dinner?",
          suspectResponse = "\"I wrote: 'Alistair is terrified of his doctor.' Alistair told me this afternoon that Ward was poisoning his mind with fake diagnoses!\"",
          bodyLanguageTell = "Taps her pen firmly against her leather journal.",
          revealsClueHint = "Corroborates that Dr. Ward was manipulating Alistair's medical charts."
        )
      ),
      suspicionRating = 3,
      assignedGuestName = "Guest 6",
      guestPasscode = "6006"
    )
  )

  val clues = listOf(
    CrimeSceneClue(
      id = "clue_01",
      code = "CLUE-01",
      name = "The Shattered Vintage Port Glass",
      category = EvidenceCategory.TOXIN,
      physicalZone = "Backyard Patio / Garden Gazebo",
      physicalStagingTip = "Place a broken or empty wine glass on a patio table or stone surface next to an evidence card labeled 'EXHIBIT 1'.",
      description = "Crystal stemware shattered on the flagstones. The dregs of dark crimson vintage port emit a faint bitter almond and peppery scent.",
      forensicDetails = "Spectrometry analysis: Concentrated Aconitine neurotoxin detected. Fatal dose was contained in a single swallow. Lethal ingestion window: 7:45 - 7:55 PM.",
      pointsToSuspectId = "dr_ward",
      testReagentResult = "REAGENT TEST POSITIVE: Deep violet reaction confirms pure Aconite extract (Monkshood).",
      hiddenUvSecret = "UV FLUORESCENCE: Lip rim contains traces of Lord Vance's saliva + concentrated Monkshood alkaloid.",
      isDiscovered = true
    ),
    CrimeSceneClue(
      id = "clue_02",
      code = "CLUE-02",
      name = "Monogrammed Gold Fountain Pen ('J.W.')",
      category = EvidenceCategory.PERSONAL,
      physicalZone = "Living Room / Drinks Bar",
      physicalStagingTip = "Tuck an antique or gold pen behind a bottle on the kitchen counter or bar cart.",
      description = "A heavy 18k gold fountain pen engraved with the initials 'J.W.'. Found dropped between the drinks trolley and the velvet curtain.",
      forensicDetails = "Matches Dr. Julian Ward's personal stationery set. Microscopic residue of pharmaceutical dropper lubricant found on the pen barrel.",
      pointsToSuspectId = "dr_ward",
      testReagentResult = "LATENT TRACE: Matches physician grade sterile dropper lubricant.",
      hiddenUvSecret = "UV FLUORESCENCE: Clear fingerprints belonging to Dr. Julian Ward across the pen grip.",
      isDiscovered = false
    ),
    CrimeSceneClue(
      id = "clue_03",
      code = "CLUE-03",
      name = "Bloody Bronze Fireplace Poker",
      category = EvidenceCategory.WEAPON,
      physicalZone = "Patio Flower Bed / Backyard Shrub",
      physicalStagingTip = "Hide a metal rod, grill poker, or fireplace tool in garden bushes or behind an outdoor potted plant.",
      description = "Heavy bronze poker with blood and gray stone dust on the tip. Hidden hastily inside the rhododendron bushes.",
      forensicDetails = "Blood matches Lord Vance. However, forensic analysis confirms the victim's heart had stopped beating before the blow was struck—no arterial spray or capillary response!",
      pointsToSuspectId = "dr_ward",
      testReagentResult = "POST-MORTEM CONFIRMATION: Absence of vital reaction proves head trauma occurred AFTER lethal cardiac arrest.",
      hiddenUvSecret = "UV FLUORESCENCE: Smudged wool fibers matching Dr. Ward's charcoal suit sleeve on the handle.",
      isDiscovered = false
    ),
    CrimeSceneClue(
      id = "clue_04",
      code = "CLUE-04",
      name = "Torn Forged Will Draft",
      category = EvidenceCategory.DOCUMENT,
      physicalZone = "Study / Desk Area",
      physicalStagingTip = "Fold a torn paper with legal handwriting behind a book or under a desk lamp.",
      description = "Legal stationery declaring: 'I leave all estate assets solely to my daughter Elena...' The notary signature is amateurishly forged with blue ballpoint pen.",
      forensicDetails = "Ink analysis matches the gel pen found in Elena Vance's designer handbag.",
      pointsToSuspectId = "elena_vance",
      testReagentResult = "FORENSIC HANDWRITING MATCH: 98% match to Elena Vance's handwriting specimen.",
      hiddenUvSecret = "UV FLUORESCENCE: Faint indented handwriting reveals debt balance: '$850,000 DUE MIDNIGHT TO CASINO'.",
      isDiscovered = false
    ),
    CrimeSceneClue(
      id = "clue_05",
      code = "CLUE-05",
      name = "Muddy Work Bootprint (Size 11 Peat)",
      category = EvidenceCategory.TRACE,
      physicalZone = "Garden Gazebo / Arbor Entrance",
      physicalStagingTip = "Dust flour or chalk in the shape of a boot tread near the doorway or outdoor steps.",
      description = "A heavy lug-sole boot impression pressed into the damp garden loam leading into the arbor.",
      forensicDetails = "Tread pattern exactly matches Marcus Sterling's heavy gardening boots. However, the stride pattern indicates running AWAY from the body at 8:20 PM.",
      pointsToSuspectId = "marcus_sterling",
      testReagentResult = "SOIL MATCH: Soil contains greenhouse peat moss and crushed perlite unique to Marcus's nursery.",
      hiddenUvSecret = "UV FLUORESCENCE: Peat compound contains trace greenhouse plant fertilizer code 'VANCE-FLORA-9'.",
      isDiscovered = false
    ),
    CrimeSceneClue(
      id = "clue_06",
      code = "CLUE-06",
      name = "Silver Makeup Compact with Hidden Drawer",
      category = EvidenceCategory.TOXIN,
      physicalZone = "Powder Room / Bathroom Vanity",
      physicalStagingTip = "Place a vintage makeup compact or mirrored case on a bathroom shelf or side table.",
      description = "An ornate silver compact engraved with Vivienne's initials. Opening the false mirror bottom reveals a reservoir of fine white powder.",
      forensicDetails = "Chemical test: Pure Arsenic Trioxide powder! However, toxicology confirms Lord Vance suffered NO arsenic poisoning—his tea was never consumed!",
      pointsToSuspectId = "vivienne_delacroix",
      testReagentResult = "CHEMICAL RESULT: Arsenic Trioxide. Completely distinct from the lethal Aconite that killed Vance.",
      hiddenUvSecret = "UV FLUORESCENCE: White powder glows bright yellow-white under 365nm UV, confirming Arsenic Trioxide.",
      isDiscovered = false
    ),
    CrimeSceneClue(
      id = "clue_07",
      code = "CLUE-07",
      name = "Victim's Dying Handkerchief Note",
      category = EvidenceCategory.DOCUMENT,
      physicalZone = "Crime Scene / Victim's Clenched Hand",
      physicalStagingTip = "Place a folded white napkin or handkerchief marked with faint letters 'J.W. PORT SHED' near the crime scene center.",
      description = "A bloodstained silk handkerchief recovered from Vance's left fist. Scratched hastily with a fountain pen in shaky handwriting.",
      forensicDetails = "Decoded under UV light: 'J.W. ... Port ... Check Shed Safe'. Lord Vance identified his murderer with his dying breath!",
      pointsToSuspectId = "dr_ward",
      testReagentResult = "UV FLUORESCENCE CONFIRMED: Lord Alistair Vance's authentic dying cursive script.",
      hiddenUvSecret = "DYING MESSAGE REVEALED: 'J.W. ... THE PORT WAS POISON ... CHECK SHED SAFE FOR AUDIT. - ALISTAIR'",
      isDiscovered = false
    ),
    CrimeSceneClue(
      id = "clue_08",
      code = "CLUE-08",
      name = "Audited Medical Foundation Ledger",
      category = EvidenceCategory.DOCUMENT,
      physicalZone = "Garden Shed / Garage Toolbench",
      physicalStagingTip = "Hide a folder or notepad marked 'CONFIDENTIAL AUDIT' under a garden tool or toolbox in the shed or garage.",
      description = "Accounting documents showing $3,200,000 transferred from the Vance Medical Trust into Dr. Julian Ward's offshore accounts. Alistair wrote 'POLICE ARREST MONDAY' in red ink across the top.",
      forensicDetails = "Definitive motive document. Lord Vance was about to expose Dr. Ward to Interpol and destroy his medical career.",
      pointsToSuspectId = "dr_ward",
      testReagentResult = "FINGERPRINT MATCH: Latent fingerprints belonging to both Lord Vance and Dr. Julian Ward.",
      hiddenUvSecret = "UV FLUORESCENCE: Notarized bank transfer vouchers stamped with Dr. Julian Ward's medical license number.",
      isDiscovered = false
    ),
    CrimeSceneClue(
      id = "clue_09",
      code = "CLUE-09",
      name = "Manually Tripped Breaker Switch",
      category = EvidenceCategory.TRACE,
      physicalZone = "Basement / Utility Room / Garage Panel",
      physicalStagingTip = "Tag the electrical breaker box or a light switch with a yellow caution label.",
      description = "The main 200-amp master circuit breaker for the estate. Inspection shows it was not tripped by a lightning surge—it was deliberately pulled down with a wrench.",
      forensicDetails = "Grease mark on the switch lever matches the mechanical oil found in Marcus's shed tools.",
      pointsToSuspectId = "marcus_sterling",
      testReagentResult = "TOOL MARK ANALYSIS: Jaw width of wrench matches Marcus's 10-inch crescent wrench.",
      hiddenUvSecret = "UV FLUORESCENCE: Mechanical grease matches the oil lubricants stored in the generator shed.",
      isDiscovered = false
    )
  )
}
