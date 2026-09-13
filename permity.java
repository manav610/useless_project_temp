import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Useless Ask: Permission to Do Useless Things.
 *
 * A self-contained Java Swing hackathon app. It uses only the Java standard
 * library, so no Maven, Gradle, APIs, real politicians, or external images
 * are required.
 */
public class PermitlyApp {
    private static final Color INK = new Color(19, 14, 33);
    private static final Color SIDEBAR = new Color(22, 16, 38);
    private static final Color PANEL = new Color(42, 33, 65);
    private static final Color PANEL_LIGHT = new Color(54, 43, 84);
    private static final Color PURPLE = new Color(157, 126, 245);
    private static final Color GOLD = new Color(248, 201, 87);
    private static final Color PINK = new Color(241, 107, 155);
    private static final Color TEXT = new Color(243, 239, 255);
    private static final Color MUTED = new Color(183, 174, 207);
    private static final Color GREEN = new Color(93, 213, 161);
    private static final Color INPUT = new Color(27, 21, 45);

    private final Random random = new Random();
    private final List<Permit> history = new ArrayList<>();
    private final List<Department> departments = Arrays.asList(
            new Department("🏛", "Ministry of Tiny Decisions", "Checking whether this truly needed a government form."),
            new Department("🧑‍⚖️", "Department of Official Vibes", "Measuring the seriousness of your unserious request."),
            new Department("🕵️", "Bureau of Specific Requests", "Searching for any detail we can make more complicated."),
            new Department("🗂", "Central Paper-Shuffling Authority", "Moving your file from the left pile to the right pile."),
            new Department("🧓", "Office of the Honourable Chairperson", "Nodding thoughtfully after reading only the subject line."),
            new Department("🪄", "Directorate of Mildly Magical Affairs", "Consulting a very official-looking crystal ball."),
            new Department("📠", "Fax and Fax-Related Innovations Cell", "Sending a confirmation fax directly into the void.")
    );
    private final List<String> conditions = Arrays.asList(
            "You must look moderately responsible while doing it.",
            "At least one friend must say: bhai, really?",
            "This approval expires immediately after it becomes useful.",
            "Please do not explain this permit to an accountant.",
            "Maintain a dignified face for a minimum of 4.5 seconds.",
            "One dramatic sigh is legally recommended before proceeding.",
            "The Ministry accepts no responsibility for awkward family questions."
            "The Ministry accepts no responsibility for awkward family questions.",
            "Do not make eye contact with this certificate after midnight.",
            "If challenged, blame the Department of Vibes with full confidence.",
            "This permission must be exercised with 0% confidence and 100% commitment.",
            "One biscuit must be consumed in honour of the five committees involved."
    );
    private final List<String> comments = Arrays.asList(
            "The file has been stamped with a stamp-shaped stamp.",
            "The committee found the request gloriously unnecessary.",
            "An intern asked why. We promoted them to Senior Why Analyst.",
            "The budget impact is exactly one imaginary biscuit.",
            "Cabinet members agree this could have been an email. It was not.",
            "A panel of experts stared at the form for 11 seconds. Consensus achieved."
    );
    private final List<String> memos = Arrays.asList(
            "Public notice: Do not use this permit to win an argument with your sibling.",
            "Breaking: The Ministry processed a thought. Nobody knows whose.",
            "Compliance reminder: Smiling at the certificate is optional but encouraged.",
            "Urgent circular: The queue for nothing is moving surprisingly slowly.",
            "Official clarification: Just because is a constitutionally valid reason here."
    );
    private final List<String> missionReplies = Arrays.asList(
            "After reading ‘%s’, the committee took a deep breath and approved it before anyone could ask why.",
            "Your mission ‘%s’ has been classified as emotionally important and logically unnecessary. Perfect.",
            "The Cabinet reviewed ‘%s’ and agreed it deserves at least three forms and one dramatic stamp.",
            "Regarding ‘%s’: the Ministry supports your bravery, your confidence, and none of your planning.",
            "Experts confirmed that ‘%s’ is exactly the kind of tiny decision this nation was built to overthink."
    );
    private final List<String> witnessReplies = Arrays.asList(
            "%s has been listed as a witness and is now 12%% more official than before.",
            "We contacted %s spiritually. Their confused silence has been accepted as testimony.",
            "%s may never know about this, but the Ministry thanks them for their invisible service.",
            "Witness %s has been granted temporary immunity from follow-up questions.",
            "%s has bravely watched this process happen. A tiny medal may arrive never."
    );
    private final List<FeaturedCase> featuredCases = Arrays.asList(
            new FeaturedCase("Riya K.", "Ignoring a group-chat notification for 36 hours", "PUT-FEAT-001", "The silence was approved as self-care."),
            new FeaturedCase("Arjun D.", "Calling a nap a strategic energy summit", "PUT-FEAT-042", "Approved with one pillow-related condition."),
            new FeaturedCase("Sana M.", "Buying another black hoodie despite owning six", "PUT-FEAT-078", "The Cabinet called it a national wardrobe emergency."),
            new FeaturedCase("Kabir P.", "Staring at the fridge and finding absolutely nothing", "PUT-FEAT-108", "A historic act of kitchen-based research.")
    );

    private JFrame frame;
    private JTextField nameField;
    private JTextField witnessField;
    private JTextArea missionArea;
    private JComboBox<String> purposeBox;
    private JComboBox<String> urgencyBox;
    private JCheckBox uselessCheck;
    private JPanel approvalBox;
    private JPanel certificateHolder;
    private JPanel historyHolder;
    private JLabel circularLabel;
    private JLabel issuedMetric;
    private Permit activePermit;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PermitlyApp().createAndShow());
    }

    private void createAndShow() {
        configureSwingDefaults();
        frame = new JFrame("Useless Ask — Officially Unnecessary");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(980, 680));
        frame.setSize(1280, 820);
        frame.setLocationRelativeTo(null);

        GradientPanel root = new GradientPanel();
        root.setLayout(new BorderLayout());
        root.add(buildSidebar(), BorderLayout.WEST);
        root.add(buildTabs(), BorderLayout.CENTER);

        frame.setContentPane(root);
        frame.setVisible(true);
    }

    private void configureSwingDefaults() {
        UIManager.put("TabbedPane.selected", PANEL);
        UIManager.put("TabbedPane.background", INK);
        UIManager.put("TabbedPane.foreground", TEXT);
        UIManager.put("TabbedPane.font", new Font("SansSerif", Font.BOLD, 14));
        UIManager.put("OptionPane.background", PANEL);
        UIManager.put("Panel.background", INK);
    }

    private JComponent buildSidebar() {
        JPanel side = new JPanel();
        side.setBackground(SIDEBAR);
        side.setLayout(new BoxLayout(side, BoxLayout.Y_AXIS));
        side.setBorder(new EmptyBorder(24, 20, 22, 20));
        side.setPreferredSize(new Dimension(250, 1));

        JPanel brand = new JPanel(new BorderLayout(12, 0));
        brand.setOpaque(false);
        brand.setMaximumSize(new Dimension(220, 70));
        brand.add(new LogoMark(58), BorderLayout.WEST);
        JPanel brandWords = verticalPanel(false);
        brandWords.add(label("USELESS ASK", 22, Font.BOLD, TEXT));
        brandWords.add(label("Officially unnecessary", 11, Font.PLAIN, MUTED));
        brand.add(brandWords, BorderLayout.CENTER);
        side.add(brand);
        side.add(Box.createVerticalStrut(25));
        side.add(label("MINISTRY DASHBOARD", 11, Font.BOLD, GOLD));
        side.add(Box.createVerticalStrut(10));

        issuedMetric = metricCard("Permits issued", "1,284", "+37 today");
        side.add(issuedMetric);
        side.add(Box.createVerticalStrut(10));
        side.add(metricCard("Value created", "0%", "perfectly steady"));
        side.add(Box.createVerticalStrut(10));
        side.add(metricCard("Papers shuffled", "6,941", "+100% unnecessary"));
        side.add(Box.createVerticalStrut(18));

        side.add(label("CABINET PULSE", 11, Font.BOLD, GOLD));
        side.add(Box.createVerticalStrut(8));
        RoundedPanel pulse = new RoundedPanel(new Color(51, 40, 78), 16);
        pulse.setLayout(new BoxLayout(pulse, BoxLayout.Y_AXIS));
        pulse.setBorder(new EmptyBorder(12, 14, 12, 14));
        pulse.setMaximumSize(new Dimension(220, 110));
        pulse.add(label("🗳  Mood: concerned but photogenic", 12, Font.BOLD, new Color(233, 227, 247)));
        pulse.add(Box.createVerticalStrut(5));
        pulse.add(label("Files pending: 404  ·  Tea level: alarming", 11, Font.PLAIN, MUTED));
        pulse.add(Box.createVerticalStrut(5));
        pulse.add(label("Public confidence: statistically mysterious", 11, Font.PLAIN, new Color(113, 220, 169)));
        side.add(pulse);
        side.add(Box.createVerticalStrut(9));
        RoundedPanel queue = new RoundedPanel(new Color(44, 35, 69), 14);
        queue.setLayout(new BorderLayout(9, 0));
        queue.setBorder(new EmptyBorder(10, 12, 10, 12));
        queue.setMaximumSize(new Dimension(220, 56));
        queue.add(label("⏳", 22, Font.PLAIN, TEXT), BorderLayout.WEST);
        queue.add(label("Queue position: 1 of 1\nEstimated wait: emotionally confusing", 11, Font.PLAIN, new Color(215, 208, 233)), BorderLayout.CENTER);
        side.add(queue);
        side.add(Box.createVerticalStrut(18));

        side.add(label("CABINET STICKER PACK", 11, Font.BOLD, GOLD));
        side.add(Box.createVerticalStrut(7));
        CabinetStickerPanel stickers = new CabinetStickerPanel();
        stickers.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        stickers.setMaximumSize(new Dimension(220, 92));
        side.add(stickers);
        side.add(Box.createVerticalStrut(18));

        side.add(label("TODAY'S CIRCULAR", 11, Font.BOLD, GOLD));
        side.add(Box.createVerticalStrut(8));
        RoundedPanel memoCard = new RoundedPanel(PANEL, 16);
        memoCard.setLayout(new BorderLayout());
        memoCard.setBorder(new EmptyBorder(14, 14, 14, 14));
        memoCard.setMaximumSize(new Dimension(220, 150));
        circularLabel = new JLabel(toHtml(randomFrom(memos), 180));
        circularLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        circularLabel.setForeground(new Color(210, 202, 233));
        circularLabel.setVerticalAlignment(SwingConstants.TOP);
        memoCard.add(circularLabel, BorderLayout.CENTER);
        side.add(memoCard);
        side.add(Box.createVerticalStrut(9));

        JButton circularButton = button("Issue another circular", PANEL_LIGHT, TEXT);
        circularButton.addActionListener(e -> circularLabel.setText(toHtml(randomFrom(memos), 185)));
        side.add(circularButton);
        side.add(Box.createVerticalGlue());
        JLabel disclaimer = new JLabel(toHtml("A fictional parody ministry.\nZero legal power. Maximum paperwork.", 190));
        disclaimer.setFont(new Font("SansSerif", Font.PLAIN, 11));
        disclaimer.setForeground(MUTED);
        disclaimer.setMaximumSize(new Dimension(220, 60));
        disclaimer.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        side.add(disclaimer);
        JScrollPane sidebarScroll = new JScrollPane(side);
        sidebarScroll.setBorder(null);
        sidebarScroll.setPreferredSize(new Dimension(270, 1));
        sidebarScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sidebarScroll.getViewport().setBackground(SIDEBAR);
        sidebarScroll.getVerticalScrollBar().setUnitIncrement(16);
        return sidebarScroll;
    }

    private JTabbedPane buildTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBorder(new EmptyBorder(15, 20, 20, 20));
        tabs.addTab("  Apply for permission  ", buildApplyPage());
        tabs.addTab("  Hall of nonsense  ", buildHallPage());
        tabs.addTab("  About the Ministry  ", buildAboutPage());
        return tabs;
    }

    private JScrollPane buildApplyPage() {
        JPanel content = verticalPanel(true);
        content.setBorder(new EmptyBorder(15, 8, 42, 8));
        content.add(new HeroPanel());
        content.add(Box.createVerticalStrut(24));
        content.add(label("START AN APPLICATION", 11, Font.BOLD, GOLD));
        content.add(Box.createVerticalStrut(9));
        content.add(buildFormCard());
        content.add(Box.createVerticalStrut(15));

        approvalBox = verticalPanel(false);
        approvalBox.setVisible(false);
        approvalBox.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        content.add(approvalBox);

        certificateHolder = verticalPanel(false);
        certificateHolder.setVisible(false);
        certificateHolder.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        content.add(certificateHolder);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(INK);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        return scroll;
    }

    private RoundedPanel buildFormCard() {
        RoundedPanel card = new RoundedPanel(PANEL, 20);
        card.setLayout(new GridBagLayout());
        card.setBorder(new EmptyBorder(24, 25, 24, 25));
        card.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(1100, 470));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 5, 14);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        c.gridx = 0;
        c.gridy = 0;
        card.add(formLabel("Applicant name"), c);
        c.gridx = 1;
        card.add(formLabel("Urgency level"), c);

        nameField = input("e.g. Manav, CEO of procrastination");
        urgencyBox = combo(new String[] {"Can wait", "Slightly dramatic", "National emergency (probably not)"});
        c.gridx = 0;
        c.gridy = 1;
        card.add(nameField, c);
        c.gridx = 1;
        card.add(urgencyBox, c);

        c.gridx = 0;
        c.gridy = 2;
        c.insets = new Insets(17, 4, 5, 14);
        card.add(formLabel("Permission category"), c);
        c.gridx = 1;
        card.add(formLabel("Describe your noble mission"), c);

        purposeBox = combo(new String[] {
                "Taking a 7-minute nap",
                "Eating samosa before dinner",
                "Ignoring a group-chat notification",
                "Buying something I absolutely do not need",
                "Staring at the ceiling and calling it research",
                "Replying ‘hmm’ after three business days",
                "Wearing sunglasses indoors for no reason",
                "Opening the fridge again because maybe new food appeared",
                "Cancelling plans to protect my blanket relationship",
                "Calling an online shopping cart a financial vision board",
                "Watching one more episode under international pressure",
                "Avoiding a phone call because the battery is emotionally low",
                "Taking a screenshot instead of writing something down",
                "Using the word ‘vibes’ in a serious conversation",
                "Something even less important..."
        });
        missionArea = new JTextArea(3, 20);
        missionArea.setLineWrap(true);
        missionArea.setWrapStyleWord(true);
        missionArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        missionArea.setForeground(TEXT);
        missionArea.setBackground(INPUT);
        missionArea.setCaretColor(TEXT);
        missionArea.setBorder(new EmptyBorder(9, 11, 9, 11));
        JScrollPane missionScroll = new JScrollPane(missionArea);
        missionScroll.setBorder(new LineBorder(new Color(94, 77, 135), 1, true));
        missionScroll.getViewport().setBackground(INPUT);
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(4, 4, 6, 14);
        card.add(purposeBox, c);
        c.gridx = 1;
        card.add(missionScroll, c);

        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(17, 4, 5, 14);
        card.add(formLabel("One witness (optional but official-looking)"), c);
        c.gridx = 1;
        card.add(formLabel("Cabinet declaration"), c);

        witnessField = input("e.g. My confused roommate");
        uselessCheck = new JCheckBox("I confirm this has no meaningful contribution to society.");
        uselessCheck.setOpaque(false);
        uselessCheck.setForeground(new Color(224, 217, 242));
        uselessCheck.setFont(new Font("SansSerif", Font.PLAIN, 13));
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(4, 4, 10, 14);
        card.add(witnessField, c);
        c.gridx = 1;
        card.add(uselessCheck, c);

        JButton submit = button("Submit to the Cabinet  →", PURPLE, Color.WHITE);
        submit.setFont(new Font("SansSerif", Font.BOLD, 14));
        submit.addActionListener(this::submitApplication);
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        c.insets = new Insets(11, 4, 0, 4);
        card.add(submit, c);
        return card;
    }

    private JScrollPane buildHallPage() {
        JPanel page = verticalPanel(true);
        page.setBorder(new EmptyBorder(30, 15, 42, 15));
        page.add(label("HALL OF OFFICIAL NONSENSE", 11, Font.BOLD, GOLD));
        page.add(Box.createVerticalStrut(8));
        page.add(label("Recent citizens who dared to ask.", 31, Font.BOLD, TEXT));
        page.add(Box.createVerticalStrut(19));
        historyHolder = verticalPanel(false);
        historyHolder.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        page.add(historyHolder);
        page.add(Box.createVerticalStrut(20));
        page.add(buildNonsensePoll());
        page.add(Box.createVerticalStrut(20));
        RoundedPanel quote = new RoundedPanel(GOLD, 18);
        quote.setLayout(new BoxLayout(quote, BoxLayout.Y_AXIS));
        quote.setBorder(new EmptyBorder(26, 28, 26, 28));
        quote.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        JLabel q = label("“This could have been a thought.”", 27, Font.BOLD, new Color(42, 27, 77));
        q.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        JLabel q2 = label("— The entire Cabinet, after approving your request", 13, Font.PLAIN, new Color(66, 44, 95));
        q2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        quote.add(q);
        quote.add(Box.createVerticalStrut(8));
        quote.add(q2);
        page.add(quote);
        renderHistory();
        JScrollPane scroll = new JScrollPane(page);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(INK);
        return scroll;
    }

    private RoundedPanel buildNonsensePoll() {
        RoundedPanel poll = new RoundedPanel(new Color(48, 39, 76), 18);
        poll.setLayout(new GridBagLayout());
        poll.setBorder(new EmptyBorder(20, 22, 20, 22));
        poll.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        poll.setMaximumSize(new Dimension(1100, 190));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(3, 2, 3, 16);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        poll.add(label("PUBLIC SUGGESTION BOX · UNFORTUNATELY OPEN", 11, Font.BOLD, GOLD), c);
        c.gridy = 1;
        poll.add(label("Today’s most avoidable debate: what should become a national priority?", 19, Font.BOLD, TEXT), c);

        JRadioButton optionOne = pollOption("A protected 3 PM nap window");
        JRadioButton optionTwo = pollOption("Official approval before replying ‘k’");
        JRadioButton optionThree = pollOption("A hotline for deciding whether to order fries");
        ButtonGroup group = new ButtonGroup();
        group.add(optionOne);
        group.add(optionTwo);
        group.add(optionThree);
        optionOne.setSelected(true);
        c.gridy = 2;
        c.gridwidth = 1;
        poll.add(optionOne, c);
        c.gridx = 1;
        poll.add(optionTwo, c);
        c.gridx = 0;
        c.gridy = 3;
        poll.add(optionThree, c);

        JLabel verdict = label("The Cabinet is waiting patiently for democracy to become slightly sillier.", 12, Font.PLAIN, MUTED);
        JButton vote = button("Cast a wildly unnecessary vote", PURPLE, Color.WHITE);
        vote.setMaximumSize(new Dimension(270, 42));
        vote.addActionListener(e -> {
            String picked = optionOne.isSelected() ? optionOne.getText()
                    : optionTwo.isSelected() ? optionTwo.getText() : optionThree.getText();
            verdict.setText("<html><span style='color:#66d8a8'><b>Cabinet verdict:</b></span> ‘" + picked
                    + "’ received one vote and three dramatic press conferences.</html>");
        });
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 1;
        poll.add(verdict, c);
        c.gridx = 1;
        c.weightx = 0;
        poll.add(vote, c);
        return poll;
    }

    private JRadioButton pollOption(String text) {
        JRadioButton option = new JRadioButton(text);
        option.setOpaque(false);
        option.setForeground(new Color(226, 220, 243));
        option.setFont(new Font("SansSerif", Font.PLAIN, 12));
        option.setFocusPainted(false);
        return option;
    }

    private JScrollPane buildAboutPage() {
        JPanel page = verticalPanel(true);
        page.setBorder(new EmptyBorder(30, 15, 42, 15));
        page.add(label("ABOUT THE MINISTRY", 11, Font.BOLD, GOLD));
        page.add(Box.createVerticalStrut(8));
        page.add(label("A bureaucracy with absolutely nowhere to go.", 30, Font.BOLD, TEXT));
        page.add(Box.createVerticalStrut(22));

        JPanel features = new JPanel(new java.awt.GridLayout(1, 3, 14, 0));
        features.setOpaque(false);
        features.setMaximumSize(new Dimension(1100, 230));
        features.add(featureCard("🧠", "Artificial Unintelligence", "Our algorithms take simple questions and make them deeply administrative."));
        features.add(featureCard("📦", "Cloud of Paperwork", "Every request is stored in a digital drawer that is definitely too full."));
        features.add(featureCard("🏅", "Certified Useless", "Proudly inefficient, dramatically over-designed and completely fictional."));
        page.add(features);
        page.add(Box.createVerticalStrut(28));
        page.add(label("MEET THE COMPLETELY FICTIONAL CABINET", 11, Font.BOLD, GOLD));
        page.add(Box.createVerticalStrut(8));
        page.add(label("They campaign on one promise: more forms for everyone.", 23, Font.BOLD, TEXT));
        page.add(Box.createVerticalStrut(13));
        JPanel cabinet = new JPanel(new java.awt.GridLayout(1, 3, 14, 0));
        cabinet.setOpaque(false);
        cabinet.setMaximumSize(new Dimension(1100, 235));
        cabinet.add(politicianCard("🧓", "Hon. Pending Prasad", "Minister for Doing It Tomorrow", "Has postponed 17 urgent matters and one lunch. His policy: if it can wait, it should."));
        cabinet.add(politicianCard("👩‍⚖️", "Madam Formika Rao", "Minister of Mandatory Forms", "Believes every emotion deserves a three-page application and a stapled annexure."));
        cabinet.add(politicianCard("🧑‍💼", "Dr. Circular Verma", "Minister of Repeated Announcements", "Issues daily circulars reminding citizens about yesterday's circulars."));
        page.add(cabinet);
        page.add(Box.createVerticalStrut(21));
        RoundedPanel manifesto = new RoundedPanel(new Color(55, 42, 88), 18);
        manifesto.setLayout(new BorderLayout());
        manifesto.setBorder(new EmptyBorder(20, 22, 20, 22));
        manifesto.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        manifesto.add(label("CABINET MANIFESTO\n1. Every small decision deserves a committee.\n2. Every committee deserves snacks.\n3. Every snack deserves a stamped receipt.", 14, Font.PLAIN, new Color(230, 224, 246)), BorderLayout.CENTER);
        page.add(manifesto);
        page.add(Box.createVerticalStrut(18));
        RoundedPanel legal = new RoundedPanel(PANEL, 16);
        legal.setLayout(new BorderLayout());
        legal.setBorder(new EmptyBorder(20, 22, 20, 22));
        legal.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        legal.add(label("Tiny print: Useless Ask is a hackathon parody. Its certificates have exactly zero legal power, except perhaps in a very funny WhatsApp group.", 14, Font.PLAIN, new Color(212, 203, 237)), BorderLayout.CENTER);
        page.add(legal);

        JScrollPane scroll = new JScrollPane(page);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(INK);
        return scroll;
    }

    private void submitApplication(ActionEvent event) {
        String applicant = nameField.getText().trim();
        if (applicant.isEmpty()) {
            message("Every great useless request needs an applicant name.");
            return;
        }
        if (!uselessCheck.isSelected()) {
            message("Please certify the uselessness. The cabinet is strict about this part.");
            return;
        }
        String selectedPurpose = (String) purposeBox.getSelectedItem();
        String mission = missionArea.getText().trim();
        String purpose = selectedPurpose.startsWith("Something") && !mission.isEmpty() ? mission : selectedPurpose;
        String missionForReply = mission.isEmpty() ? purpose : mission;
        activePermit = makePermit(applicant, purpose, missionForReply, (String) urgencyBox.getSelectedItem(), witnessField.getText().trim());
        history.add(0, activePermit);
        renderHistory();
        refreshMetrics();
        startApproval(activePermit);
    }

    private Permit makePermit(String name, String purpose, String mission, String urgency, String witness) {
        List<Department> selected = new ArrayList<>(departments);
        Collections.shuffle(selected, random);
        selected = new ArrayList<>(selected.subList(0, 5));
        LocalDateTime now = LocalDateTime.now();
        String number = "PUT-" + now.format(DateTimeFormatter.ofPattern("HHmmss")) + "-" + Integer.toHexString(random.nextInt(0xFFFF)).toUpperCase();
        String signature = randomFrom(Arrays.asList(
                "Pending Kumar (finally)",
                "Dr. B. K. Yaar, reluctantly",
                "S. Stamp, Esq. (probably)",
                "Hon. Lefty, on a tea break",
                "R. T. Form, eyewitness to nothing"
        ));
        String title = randomFrom(Arrays.asList(
                "Minister of Pretending This Matters",
                "Chief Officer, Naps & National Affairs",
                "Director of Bas Ek Minute Operations",
                "Deputy Secretary for Avoidable Decisions",
                "Acting Head of the Snack-Related Committee"
        ));
        String displayedWitness = witness.isEmpty() ? "An emotionally neutral bystander" : witness;
        String missionReply = String.format(randomFrom(missionReplies), mission);
        String witnessReply = witness.isEmpty()
                ? "No witness was submitted. The Cabinet has appointed an emotionally neutral bystander and promised not to bother them."
                : String.format(randomFrom(witnessReplies), witness);
        return new Permit(name, purpose, urgency, displayedWitness,
                number, now.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")), randomFrom(conditions),
                randomFrom(comments), missionReply, witnessReply, signature, title, selected);
    }

    private void startApproval(Permit permit) {
        approvalBox.removeAll();
        certificateHolder.removeAll();
        certificateHolder.setVisible(false);
        approvalBox.setVisible(true);
        approvalBox.add(Box.createVerticalStrut(16));
        approvalBox.add(label("THE CABINET HAS SPOKEN", 11, Font.BOLD, GOLD));
        approvalBox.add(Box.createVerticalStrut(8));
        approvalBox.add(label("Your request has entered the official maze.", 24, Font.BOLD, TEXT));
        approvalBox.add(Box.createVerticalStrut(12));
        JProgressBar progress = new JProgressBar(0, 100);
        progress.setValue(0);
        progress.setStringPainted(true);
        progress.setString("Opening an unnecessarily large file...");
        progress.setForeground(GREEN);
        progress.setBackground(INPUT);
        progress.setBorder(new EmptyBorder(6, 2, 6, 2));
        progress.setMaximumSize(new Dimension(1100, 36));
        approvalBox.add(progress);
        approvalBox.add(Box.createVerticalStrut(11));

        List<JLabel> statusLabels = new ArrayList<>();
        for (Department department : permit.departments) {
            RoundedPanel row = approvalRow(department, statusLabels);
            approvalBox.add(row);
            approvalBox.add(Box.createVerticalStrut(7));
        }
        JPanel reactions = new JPanel(new java.awt.GridLayout(1, 2, 10, 0));
        reactions.setOpaque(false);
        reactions.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        reactions.setMaximumSize(new Dimension(1100, 126));
        reactions.add(reactionCard("CABINET OBSERVATION", "🧠", permit.missionReply, new Color(61, 46, 97)));
        reactions.add(reactionCard("WITNESS DESK REPLY", "🕵️", permit.witnessReply, new Color(59, 57, 92)));
        approvalBox.add(Box.createVerticalStrut(7));
        approvalBox.add(reactions);
        approvalBox.add(Box.createVerticalStrut(7));
        approvalBox.revalidate();
        approvalBox.repaint();

        final int[] index = {0};
        Timer timer = new Timer(560, null);
        timer.addActionListener(e -> {
            if (index[0] < permit.departments.size()) {
                Department current = permit.departments.get(index[0]);
                statusLabels.get(index[0]).setText("CLEARED");
                statusLabels.get(index[0]).setForeground(GREEN);
                progress.setValue((index[0] + 1) * 20);
                progress.setString("File being admired by " + current.name + "...");
                index[0]++;
            } else {
                timer.stop();
                progress.setString("All procedures completed with magnificent inefficiency.");
                showCertificate(permit);
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private RoundedPanel approvalRow(Department department, List<JLabel> statusLabels) {
        RoundedPanel row = new RoundedPanel(new Color(46, 37, 72), 13);
        row.setLayout(new BorderLayout(14, 0));
        row.setBorder(new EmptyBorder(10, 15, 10, 15));
        row.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(1100, 68));
        JLabel emoji = label(department.emoji, 25, Font.PLAIN, TEXT);
        row.add(emoji, BorderLayout.WEST);
        JPanel text = verticalPanel(false);
        text.add(label(department.name, 14, Font.BOLD, TEXT));
        text.add(wrappedLabel(department.action, 550, 11, Font.PLAIN, MUTED));
        row.add(text, BorderLayout.CENTER);
        JLabel status = label("IN REVIEW", 10, Font.BOLD, GOLD);
        status.setHorizontalAlignment(SwingConstants.RIGHT);
        row.add(status, BorderLayout.EAST);
        statusLabels.add(status);
        return row;
    }

    private RoundedPanel reactionCard(String heading, String emoji, String reply, Color background) {
        RoundedPanel card = new RoundedPanel(background, 15);
        card.setLayout(new BorderLayout(12, 0));
        card.setBorder(new EmptyBorder(14, 15, 14, 15));
        card.add(label(emoji, 28, Font.PLAIN, TEXT), BorderLayout.WEST);
        JPanel words = verticalPanel(false);
        words.add(label(heading, 10, Font.BOLD, GOLD));
        words.add(Box.createVerticalStrut(5));
        words.add(wrappedLabel(reply, 385, 12, Font.PLAIN, new Color(232, 227, 246)));
        card.add(words, BorderLayout.CENTER);
        return card;
    }

    private void showCertificate(Permit permit) {
        certificateHolder.removeAll();
        certificateHolder.setVisible(true);
        certificateHolder.add(Box.createVerticalStrut(26));
        certificateHolder.add(label("YOUR VERY REAL-LOOKING, VERY USELESS CERTIFICATE", 11, Font.BOLD, GOLD));
        certificateHolder.add(label("YOUR QUESTIONABLY OFFICIAL CERTIFICATE", 11, Font.BOLD, GOLD));
        certificateHolder.add(Box.createVerticalStrut(10));
        CertificatePanel certificate = new CertificatePanel(permit);
        certificate.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        certificate.setMaximumSize(new Dimension(1100, 805));
        certificateHolder.add(certificate);
        certificateHolder.add(Box.createVerticalStrut(12));
        JButton save = button("Save printable certificate (HTML)", PANEL_LIGHT, TEXT);
        JButton save = button("Save this suspiciously official certificate (HTML)", PANEL_LIGHT, TEXT);
        save.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        save.addActionListener(e -> saveCertificate(permit));
        certificateHolder.add(save);
        certificateHolder.add(Box.createVerticalStrut(10));
        certificateHolder.add(label("Tip: Open the saved HTML file in any browser and use Print for a shareable certificate.", 12, Font.PLAIN, MUTED));
        certificateHolder.revalidate();
        certificateHolder.repaint();
    }

    private void saveCertificate(Permit permit) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save your Useless Ask certificate");
        chooser.setSelectedFile(new File("useless-ask-" + permit.number.toLowerCase() + ".html"));
        if (chooser.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File destination = chooser.getSelectedFile();
        try {
            Files.writeString(destination.toPath(), certificateHtml(permit), StandardCharsets.UTF_8);
            JOptionPane.showMessageDialog(frame, "Certificate saved. It remains legally useless, but very pretty.", "Useless Ask", JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(frame, "Certificate saved. Please misuse this authority responsibly-ish.", "Useless Ask", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(frame, "Could not save the certificate: " + exception.getMessage(), "Useless Ask", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String certificateHtml(Permit permit) {
        return """
                <!doctype html><html><head><meta charset='utf-8'><title>Useless Ask Certificate</title><style>
                body{margin:0;background:#171126;display:grid;place-items:center;min-height:100vh;font-family:Arial,sans-serif}
                .cert{max-width:980px;margin:30px;background:linear-gradient(135deg,#fffdf7,#f1ebff);padding:22px;border:7px solid #30205a;border-radius:26px;color:#2d1c55}
                header{background:linear-gradient(90deg,#2d1c55,#623b9d);padding:26px;border-radius:15px;color:white}.brand{font-size:36px;font-weight:bold}.sub{color:#dfd2ff;letter-spacing:2px;font-size:12px;margin-top:7px}.file{float:right;text-align:right;color:#f4ecff;font:12px monospace;line-height:1.7}
                h1{text-align:center;font-family:Georgia,serif;font-size:48px;margin:32px 0 8px}.approved{text-align:center;color:#623b9d;font-weight:bold;letter-spacing:4px;margin-top:24px}.name{text-align:center;font: bold 42px Georgia,serif;margin:28px 0}.purpose{text-align:center;color:#623b9d;font-size:27px;font-weight:bold;margin:20px auto;max-width:760px}.facts{display:grid;grid-template-columns:1fr 1fr 1fr;gap:10px;margin:26px 0 0}.fact{border:1px solid #d8c9f3;background:#fbf9ff;padding:12px;border-radius:10px;font-size:13px;color:#463366}.fact b{font-size:10px;letter-spacing:1px;color:#2d1c55}.notes{display:grid;grid-template-columns:1fr 1fr;gap:14px;margin:14px 0 20px}.note{background:#f0eafd;padding:17px;border-radius:12px;font-size:14px;color:#463366}.note b{font-size:11px;letter-spacing:1px;color:#2d1c55}.condition{background:#eee5fc;padding:20px;border-radius:12px;margin-top:25px}.stamp{float:right;border:6px solid #bb254e;color:#bb254e;border-radius:50%;padding:28px 13px;text-align:center;font-weight:bold;transform:rotate(-12deg);margin:10px}.footer{margin-top:50px;color:#6b5b83;font-size:13px;clear:both}.signature{text-align:right;font-family:cursive;font-size:25px;margin-top:35px}
                h1{text-align:center;font-family:Georgia,serif;font-size:48px;margin:32px 0 8px}.approved{text-align:center;color:#623b9d;font-weight:bold;letter-spacing:4px;margin-top:24px}.name{text-align:center;font: bold 42px Georgia,serif;margin:28px 0}.purpose{text-align:center;color:#623b9d;font-size:27px;font-weight:bold;margin:20px auto;max-width:760px}.facts{display:grid;grid-template-columns:1fr 1fr 1fr;gap:10px;margin:26px 0 0}.fact{border:1px solid #d8c9f3;background:#fbf9ff;padding:12px;border-radius:10px;font-size:13px;color:#463366}.fact b{font-size:10px;letter-spacing:1px;color:#2d1c55}.notes{display:grid;grid-template-columns:1fr 1fr;gap:14px;margin:14px 0 20px}.note{background:#f0eafd;padding:17px;border-radius:12px;font-size:14px;color:#463366}.note b{font-size:11px;letter-spacing:1px;color:#2d1c55}.condition{background:#eee5fc;padding:20px;border-radius:12px;margin-top:25px}.stamp{float:right;border:6px solid #bb254e;color:#bb254e;border-radius:50%;padding:28px 13px;text-align:center;font-weight:bold;transform:rotate(-12deg);margin:10px}.warning{margin-top:16px;padding:12px 15px;border:2px dashed #ef5f84;border-radius:10px;color:#8c2546;font-weight:bold;text-align:center}.footer{margin-top:50px;color:#6b5b83;font-size:13px;clear:both}.signature{text-align:right;font-family:cursive;font-size:25px;margin-top:35px}.signature small{font:12px Arial;color:#6b5b83}
                </style></head><body><section class='cert'><header><div class='brand'>📜 USELESS ASK</div><div class='sub'>MINISTRY OF ABSOLUTELY NECESSARY NONSENSE · %s</div></header>
                <div class='approved'>ISSUED UNDER THE USELESS PERMISSIONS ACT · 2026<br><br>CERTIFICATE OF OFFICIAL PERMISSION</div><h1>ABSOLUTELY APPROVED</h1><p style='text-align:center;font-size:19px'>This unnecessarily formal document confirms that</p><div class='name'>%s</div><p style='text-align:center;font-size:19px'>is officially permitted to:</p><div class='purpose'>%s</div><section class='facts'><div class='fact'><b>FILE NUMBER</b><br><br>%s</div><div class='fact'><b>URGENCY CLASSIFICATION</b><br><br>%s</div><div class='fact'><b>WITNESS ON FILE</b><br><br>%s</div></section><section class='notes'><div class='note'><b>CABINET NOTE ON THE NOBLE MISSION</b><br><br>%s</div><div class='note'><b>WITNESS DESK REACTION</b><br><br>%s</div></section><div class='stamp'>100%% OFFICIAL<br>0%% USEFUL</div><div class='condition'><b>CONDITION OF APPROVAL</b><br><br>%s</div><div class='signature'>%s<br><span style='font:13px Arial'>%s</span></div><div class='footer'>Issued on %s · Valid until somebody asks a sensible question.</div></section></body></html>
                <div class='approved'>ISSUED UNDER SECTION 404: COMMON SENSE NOT FOUND<br><br>CERTIFICATE OF QUESTIONABLE NECESSITY</div><h1>APPROVED (SOMEHOW)</h1><p style='text-align:center;font-size:19px'>This excessively laminated-looking document confirms that</p><div class='name'>%s</div><p style='text-align:center;font-size:19px'>is hereby allowed to attempt:</p><div class='purpose'>%s</div><section class='facts'><div class='fact'><b>FILE STATUS</b><br><br>Looked at briefly · %s</div><div class='fact'><b>URGENCY</b><br><br>Emotionally dramatic · %s</div><div class='fact'><b>WITNESS STATUS</b><br><br>Unfortunately involved · %s</div></section><section class='notes'><div class='note'><b>WHY DID THE CABINET SAY YES?</b><br><br>%s</div><div class='note'><b>WITNESS DESK PANIC REPORT</b><br><br>%s</div></section><div class='stamp'>STAMPED AT<br>2%% BATTERY</div><div class='condition'><b>ONE CONDITION (WE NEEDED TO FEEL IMPORTANT)</b><br><br>%s</div><div class='warning'>Warning: This permit is not accepted by parents, professors, banks, or anyone with common sense.</div><div class='signature'>%s<br><span style='font:13px Arial'>%s</span><br><small>Signed during a meeting that could have been a nap.</small></div><div class='footer'>Issued on %s · Valid until a responsible adult asks one reasonable question.</div></section></body></html>
                """.formatted(escapeHtml(permit.number), escapeHtml(permit.name), escapeHtml(permit.purpose), escapeHtml(permit.number), escapeHtml(permit.urgency), escapeHtml(permit.witness), escapeHtml(permit.missionReply), escapeHtml(permit.witnessReply), escapeHtml(permit.condition), escapeHtml(permit.signature), escapeHtml(permit.title), escapeHtml(permit.date));
    }

    private void renderHistory() {
        if (historyHolder == null) {
            return;
        }
        historyHolder.removeAll();
        historyHolder.add(label("FEATURED FILES FROM THE NATIONAL ARCHIVE", 11, Font.BOLD, GOLD));
        historyHolder.add(Box.createVerticalStrut(8));
        for (FeaturedCase featured : featuredCases) {
            historyHolder.add(historyRow("🏛", featured.name, featured.purpose, featured.number, featured.note, new Color(48, 39, 74)));
            historyHolder.add(Box.createVerticalStrut(8));
        }
        if (!history.isEmpty()) {
            historyHolder.add(Box.createVerticalStrut(10));
            historyHolder.add(label("YOUR FRESHLY APPROVED FILES", 11, Font.BOLD, GOLD));
            historyHolder.add(Box.createVerticalStrut(8));
            for (Permit permit : history) {
                historyHolder.add(historyRow("📜", permit.name, permit.purpose, permit.number + " · " + permit.date, permit.comment, PANEL));
                historyHolder.add(Box.createVerticalStrut(8));
            }
        }
        historyHolder.revalidate();
        historyHolder.repaint();
    }

    private RoundedPanel historyRow(String emoji, String applicant, String purpose, String fileNumber, String note, Color background) {
        RoundedPanel row = new RoundedPanel(background, 15);
        row.setLayout(new BorderLayout(15, 0));
        row.setBorder(new EmptyBorder(13, 17, 13, 17));
        row.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        row.setMaximumSize(new Dimension(1100, 86));
        row.add(label(emoji, 27, Font.PLAIN, TEXT), BorderLayout.WEST);
        JPanel words = verticalPanel(false);
        words.add(wrappedLabel(applicant + " requested permission for: " + purpose, 740, 14, Font.BOLD, TEXT));
        words.add(wrappedLabel(fileNumber + " · " + note, 740, 11, Font.PLAIN, MUTED));
        row.add(words, BorderLayout.CENTER);
        JLabel approved = label("APPROVED", 10, Font.BOLD, GREEN);
        row.add(approved, BorderLayout.EAST);
        return row;
    }

    private void refreshMetrics() {
        if (issuedMetric != null) {
            issuedMetric.setText(toHtml("<b>Permits issued</b><br><span style='font-size:22px;color:#f4efff'>" + (1284 + history.size()) + "</span><br><span style='color:#66d8a8'>+1 magnificent file</span>", 190));
        }
    }

    private RoundedPanel featureCard(String emoji, String title, String description) {
        RoundedPanel card = new RoundedPanel(PANEL, 18);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(21, 20, 21, 20));
        card.add(label(emoji, 34, Font.PLAIN, TEXT));
        card.add(Box.createVerticalStrut(9));
        card.add(label(title, 17, Font.BOLD, GOLD));
        card.add(Box.createVerticalStrut(7));
        card.add(wrappedLabel(description, 250, 13, Font.PLAIN, MUTED));
        return card;
    }

    private RoundedPanel politicianCard(String emoji, String name, String ministry, String bio) {
        RoundedPanel card = new RoundedPanel(new Color(49, 39, 77), 18);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(19, 19, 19, 19));
        card.add(label(emoji, 35, Font.PLAIN, TEXT));
        card.add(Box.createVerticalStrut(8));
        card.add(wrappedLabel(name, 250, 17, Font.BOLD, GOLD));
        card.add(Box.createVerticalStrut(3));
        card.add(wrappedLabel(ministry, 250, 11, Font.BOLD, new Color(151, 219, 180)));
        card.add(Box.createVerticalStrut(9));
        card.add(wrappedLabel(bio, 250, 12, Font.PLAIN, MUTED));
        return card;
    }

    private JLabel metricCard(String title, String value, String hint) {
        JLabel item = label("<b>" + title + "</b><br><span style='font-size:22px;color:#f4efff'>" + value + "</span><br><span style='color:#66d8a8'>" + hint + "</span>", 12, Font.PLAIN, new Color(204, 196, 225));
        item.setBorder(new EmptyBorder(11, 12, 11, 12));
        item.setOpaque(true);
        item.setBackground(PANEL);
        item.setMaximumSize(new Dimension(220, 80));
        return item;
    }

    private JTextField input(String hint) {
        JTextField field = new JTextField();
        field.setToolTipText(hint);
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setForeground(TEXT);
        field.setCaretColor(TEXT);
        field.setBackground(INPUT);
        field.setBorder(new EmptyBorder(10, 11, 10, 11));
        field.setPreferredSize(new Dimension(200, 42));
        return field;
    }

    private JComboBox<String> combo(String[] values) {
        JComboBox<String> box = new JComboBox<>(values);
        box.setFont(new Font("SansSerif", Font.PLAIN, 13));
        box.setBackground(INPUT);
        box.setForeground(TEXT);
        box.setBorder(new LineBorder(new Color(94, 77, 135), 1, true));
        box.setPreferredSize(new Dimension(200, 42));
        return box;
    }

    private JLabel formLabel(String value) {
        return label(value, 12, Font.BOLD, new Color(229, 222, 247));
    }

    private JButton button(String title, Color background, Color foreground) {
        JButton button = new JButton(title);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(foreground);
        button.setBackground(background);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(11, 15, 11, 15));
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(1100, 46));
        return button;
    }

    private JPanel verticalPanel(boolean opaque) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(opaque);
        if (opaque) {
            panel.setBackground(INK);
        }
        return panel;
    }

    private JLabel label(String value, int size, int style, Color color) {
        String displayed = value;
        if (!value.startsWith("<html>") && (value.contains("<b>") || value.contains("<span") || value.contains("<br>"))) {
            displayed = "<html>" + value + "</html>";
        } else if (!value.startsWith("<html>") && value.contains("\n")) {
            displayed = "<html>" + value.replace("\n", "<br>") + "</html>";
        }
        JLabel text = new JLabel(displayed);
        text.setFont(new Font("SansSerif", style, size));
        text.setForeground(color);
        text.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        return text;
    }

    private JLabel wrappedLabel(String value, int width, int size, int style, Color color) {
        JLabel text = new JLabel(toHtml(value, width));
        text.setFont(new Font("SansSerif", style, size));
        text.setForeground(color);
        text.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        return text;
    }

    private String toHtml(String value, int width) {
        if (value.startsWith("<html>")) {
            return value;
        }
        return "<html><div style='width:" + width + "px'>" + value.replace("\n", "<br>") + "</div></html>";
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    private <T> T randomFrom(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    private void message(String text) {
        JOptionPane.showMessageDialog(frame, text, "Useless Ask Cabinet", JOptionPane.WARNING_MESSAGE);
    }

    private static class Department {
        final String emoji;
        final String name;
        final String action;

        Department(String emoji, String name, String action) {
            this.emoji = emoji;
            this.name = name;
            this.action = action;
        }
    }

    private static class FeaturedCase {
        final String name;
        final String purpose;
        final String number;
        final String note;

        FeaturedCase(String name, String purpose, String number, String note) {
            this.name = name;
            this.purpose = purpose;
            this.number = number;
            this.note = note;
        }
    }

    private static class Permit {
        final String name;
        final String purpose;
        final String urgency;
        final String witness;
        final String number;
        final String date;
        final String condition;
        final String comment;
        final String missionReply;
        final String witnessReply;
        final String signature;
        final String title;
        final List<Department> departments;

        Permit(String name, String purpose, String urgency, String witness, String number, String date,
               String condition, String comment, String missionReply, String witnessReply, String signature,
               String title, List<Department> departments) {
            this.name = name;
            this.purpose = purpose;
            this.urgency = urgency;
            this.witness = witness;
            this.number = number;
            this.date = date;
            this.condition = condition;
            this.comment = comment;
            this.missionReply = missionReply;
            this.witnessReply = witnessReply;
            this.signature = signature;
            this.title = title;
            this.departments = departments;
        }
    }

    private static class RoundedPanel extends JPanel {
        private final Color fill;
        private final int radius;

        RoundedPanel(Color fill, int radius) {
            this.fill = fill;
            this.radius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(fill);
            g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    private static class GradientPanel extends JPanel {
        GradientPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setPaint(new GradientPaint(0, 0, new Color(19, 14, 33), getWidth(), getHeight(), new Color(40, 27, 73)));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    private static class LogoMark extends JComponent {
        private final int size;

        LogoMark(int size) {
            this.size = size;
            setPreferredSize(new Dimension(size, size));
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            draw((Graphics2D) graphics, 0, 0, size);
        }

        static void draw(Graphics2D graphics, int x, int y, int size) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            float scale = size / 58f;
            g.translate(x, y);
            g.scale(scale, scale);
            g.setColor(GOLD);
            g.fillRoundRect(1, 1, 56, 56, 17, 17);
            g.setColor(new Color(255, 253, 246));
            g.fillRoundRect(15, 12, 23, 31, 3, 3);
            g.setColor(new Color(92, 59, 145));
            g.setStroke(new BasicStroke(2.2f));
            g.drawLine(20, 21, 34, 21);
            g.drawLine(20, 27, 34, 27);
            g.drawLine(20, 33, 29, 33);
            g.setColor(PINK);
            g.rotate(-0.75, 37, 35);
            g.fillRoundRect(34, 29, 22, 7, 4, 4);
            g.setColor(new Color(58, 41, 94));
            g.fillPolygon(new int[] {53, 58, 56}, new int[] {29, 32, 36}, 3);
            g.dispose();
        }
    }

    /** A hand-drawn, entirely fictional politician-sticker strip. */
    private static class CabinetStickerPanel extends JComponent {
        CabinetStickerPanel() {
            setPreferredSize(new Dimension(220, 92));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(new Color(49, 39, 77));
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g.setColor(new Color(211, 196, 247));
            g.setFont(new Font("SansSerif", Font.BOLD, 9));
            g.drawString("100% FICTIONAL · 0% ELECTABLE", 12, 17);
            drawSticker(g, 24, 30, new Color(244, 195, 107), new Color(72, 122, 193), "MORE FORMS!");
            drawSticker(g, 91, 30, new Color(241, 174, 133), new Color(169, 88, 147), "NO COMMENTS");
            drawSticker(g, 158, 30, new Color(183, 132, 89), new Color(66, 137, 109), "TEA FIRST");
            g.dispose();
        }

        private void drawSticker(Graphics2D g, int x, int y, Color skin, Color suit, String slogan) {
            g.setColor(new Color(255, 253, 246));
            g.fillRoundRect(x - 7, y - 5, 57, 53, 10, 10);
            g.setColor(new Color(38, 28, 65));
            g.setStroke(new BasicStroke(1.5f));
            g.drawRoundRect(x - 7, y - 5, 57, 53, 10, 10);
            g.setColor(suit);
            g.fillRoundRect(x + 8, y + 24, 27, 18, 7, 7);
            g.setColor(skin);
            g.fillOval(x + 11, y + 4, 21, 23);
            g.setColor(new Color(49, 31, 28));
            g.fillArc(x + 10, y + 1, 23, 13, 0, 180);
            g.setColor(new Color(45, 28, 85));
            g.fillOval(x + 16, y + 13, 3, 3);
            g.fillOval(x + 25, y + 13, 3, 3);
            g.setStroke(new BasicStroke(1.6f));
            g.drawArc(x + 17, y + 16, 10, 7, 0, -180);
            g.setColor(new Color(45, 28, 85));
            g.setFont(new Font("SansSerif", Font.BOLD, 6));
            int textWidth = g.getFontMetrics().stringWidth(slogan);
            g.drawString(slogan, x + 21 - textWidth / 2, y + 52);
        }
    }

    private static class HeroPanel extends JPanel {
        HeroPanel() {
            setOpaque(false);
            setPreferredSize(new Dimension(1000, 270));
            setMaximumSize(new Dimension(1100, 270));
            setAlignmentX(JComponent.LEFT_ALIGNMENT);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setPaint(new GradientPaint(0, 0, new Color(54, 38, 93), getWidth(), getHeight(), new Color(29, 23, 50)));
            g.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
            g.setColor(new Color(247, 198, 83, 45));
            g.setStroke(new BasicStroke(28));
            g.drawOval(getWidth() - 140, getHeight() - 108, 180, 180);
            LogoMark.draw(g, 38, 77, 95);
            g.setColor(GOLD);
            g.setFont(new Font("SansSerif", Font.BOLD, 11));
            g.drawString("USELESS ASK · DEPARTMENT OF UNNECESSARY QUESTIONS", 160, 58);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Serif", Font.BOLD, 37));
            g.drawString("Ask before doing", 160, 111);
            g.setColor(GOLD);
            g.drawString("something pointless.", 160, 151);
            g.setColor(new Color(209, 200, 232));
            g.setFont(new Font("SansSerif", Font.PLAIN, 15));
            g.drawString("Because ordinary life has too few forms, stamps,", 160, 189);
            g.drawString("committees and deeply unnecessary approvals.", 160, 211);
            g.setColor(GOLD);
            g.setStroke(new BasicStroke(2));
            g.drawOval(getWidth() - 175, 45, 108, 108);
            g.setFont(new Font("SansSerif", Font.BOLD, 11));
            g.drawString("OFFICIALLY", getWidth() - 159, 94);
            g.drawString("UNNECESSARY", getWidth() - 163, 112);
            g.dispose();
        }
    }

    private static class CertificatePanel extends JComponent {
        private final Permit permit;

        CertificatePanel(Permit permit) {
            this.permit = permit;
            setPreferredSize(new Dimension(1000, 790));
            setMinimumSize(new Dimension(500, 420));
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            double sx = getWidth() / 1000.0;
            double sy = getHeight() / 790.0;
            g.scale(sx, sy);
            g.setColor(new Color(255, 253, 246));
            g.fillRoundRect(0, 0, 1000, 790, 25, 25);
            g.setColor(new Color(45, 28, 85));
            g.setStroke(new BasicStroke(6));
            g.drawRoundRect(12, 12, 976, 766, 20, 20);
            g.setPaint(new GradientPaint(35, 35, new Color(45, 28, 85), 965, 180, new Color(98, 59, 157)));
            g.fillRoundRect(34, 34, 932, 115, 15, 15);
            LogoMark.draw(g, 61, 60, 62);
            g.setColor(Color.WHITE);
            g.setFont(new Font("SansSerif", Font.BOLD, 32));
            g.drawString("USELESS ASK", 142, 88);
            g.setColor(new Color(222, 208, 255));
            g.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g.drawString("MINISTRY OF ABSOLUTELY NECESSARY NONSENSE", 144, 112);
            g.setFont(new Font("Monospaced", Font.PLAIN, 12));
            g.drawString("FILE: " + permit.number, 790, 82);
            g.drawString("5 CABINET SIGN-OFFS", 790, 108);
            g.drawString("FIVE NODS. ZERO READING.", 790, 108);
            g.setColor(new Color(238, 229, 252));
            g.fillRoundRect(222, 163, 556, 25, 12, 12);
            g.setColor(new Color(94, 61, 148));
            centered(g, "ISSUED UNDER THE USELESS PERMISSIONS ACT · 2026", 500, 181, new Font("SansSerif", Font.BOLD, 11));
            centered(g, "CERTIFICATE OF OFFICIAL PERMISSION", 500, 215, new Font("SansSerif", Font.BOLD, 15));
            centered(g, "ABSOLUTELY APPROVED", 500, 272, new Font("Serif", Font.BOLD, 39));
            centered(g, "ISSUED UNDER SECTION 404: COMMON SENSE NOT FOUND", 500, 181, new Font("SansSerif", Font.BOLD, 11));
            centered(g, "CERTIFICATE OF QUESTIONABLE NECESSITY", 500, 215, new Font("SansSerif", Font.BOLD, 15));
            centered(g, "APPROVED (SOMEHOW)", 500, 272, new Font("Serif", Font.BOLD, 39));
            g.setColor(GOLD);
            g.fillRect(314, 288, 372, 3);
            g.setColor(new Color(71, 57, 98));
            centered(g, "This unnecessarily formal document confirms that", 500, 325, new Font("Serif", Font.PLAIN, 19));
            centered(g, "This excessively laminated-looking document confirms that", 500, 325, new Font("Serif", Font.PLAIN, 19));
            g.setColor(new Color(44, 27, 79));
            centered(g, permit.name, 500, 372, new Font("Serif", Font.BOLD, 34));
            g.setColor(new Color(71, 57, 98));
            centered(g, "is officially permitted to:", 500, 408, new Font("Serif", Font.PLAIN, 19));
            centered(g, "is hereby allowed to attempt:", 500, 408, new Font("Serif", Font.PLAIN, 19));
            g.setColor(new Color(98, 59, 157));
            drawWrappedCentered(g, permit.purpose, 500, 442, 650, new Font("SansSerif", Font.BOLD, 22), 26);
            g.setColor(new Color(242, 236, 253));
            g.fillRoundRect(60, 475, 280, 56, 11, 11);
            g.fillRoundRect(360, 475, 280, 56, 11, 11);
            g.fillRoundRect(660, 475, 280, 56, 11, 11);
            g.setColor(new Color(45, 28, 85));
            g.setFont(new Font("SansSerif", Font.BOLD, 10));
            g.drawString("FILE NUMBER", 78, 495);
            g.drawString("URGENCY CLASSIFICATION", 378, 495);
            g.drawString("WITNESS ON FILE", 678, 495);
            g.drawString("FILE STATUS", 78, 495);
            g.drawString("URGENCY", 378, 495);
            g.drawString("WITNESS STATUS", 678, 495);
            g.setFont(new Font("SansSerif", Font.PLAIN, 11));
            drawWrapped(g, permit.number, 78, 516, 240, 12);
            drawWrapped(g, permit.urgency, 378, 516, 240, 12);
            drawWrapped(g, permit.witness, 678, 516, 235, 12);
            drawWrapped(g, "Looked at briefly · " + permit.number, 78, 516, 240, 12);
            drawWrapped(g, "Emotionally dramatic · " + permit.urgency, 378, 516, 240, 12);
            drawWrapped(g, "Unfortunately involved · " + permit.witness, 678, 516, 235, 12);
            g.setColor(new Color(238, 229, 252));
            g.fillRoundRect(60, 548, 412, 85, 12, 12);
            g.fillRoundRect(488, 548, 452, 85, 12, 12);
            g.setColor(new Color(45, 28, 85));
            g.setFont(new Font("SansSerif", Font.BOLD, 11));
            g.drawString("CABINET NOTE ON THE NOBLE MISSION", 80, 571);
            g.drawString("WITNESS DESK REACTION", 510, 571);
            g.drawString("WHY DID THE CABINET SAY YES?", 80, 571);
            g.drawString("WITNESS DESK PANIC REPORT", 510, 571);
            g.setFont(new Font("SansSerif", Font.PLAIN, 11));
            drawWrapped(g, permit.missionReply, 80, 591, 365, 13);
            drawWrapped(g, permit.witnessReply, 510, 591, 400, 13);
            g.setColor(new Color(238, 229, 252));
            g.fillRoundRect(60, 651, 615, 76, 12, 12);
            g.setColor(new Color(45, 28, 85));
            g.setFont(new Font("SansSerif", Font.BOLD, 11));
            g.drawString("CONDITION OF APPROVAL", 82, 676);
            g.drawString("ONE CONDITION (WE NEEDED TO FEEL IMPORTANT)", 82, 676);
            g.setFont(new Font("SansSerif", Font.PLAIN, 13));
            drawWrapped(g, permit.condition, 82, 698, 555, 17);
            g.setColor(PINK);
            g.setStroke(new BasicStroke(6));
            g.drawOval(779, 649, 125, 78);
            g.setColor(PINK);
            centered(g, "100% OFFICIAL", 841, 684, new Font("SansSerif", Font.BOLD, 12));
            centered(g, "0% USEFUL", 841, 705, new Font("SansSerif", Font.BOLD, 12));
            centered(g, "STAMPED AT", 841, 684, new Font("SansSerif", Font.BOLD, 12));
            centered(g, "2% BATTERY", 841, 705, new Font("SansSerif", Font.BOLD, 12));
            g.setColor(new Color(53, 35, 95));
            g.drawLine(724, 748, 947, 748);
            centered(g, permit.signature, 835, 741, new Font("Serif", Font.ITALIC, 16));
            centered(g, permit.title, 835, 766, new Font("SansSerif", Font.PLAIN, 10));
            g.setColor(new Color(112, 96, 142));
            g.setFont(new Font("SansSerif", Font.PLAIN, 10));
            g.drawString("Warning: not accepted by parents, professors, banks, or anyone with common sense.", 42, 747);
            g.drawString("Issued on " + permit.date + " · Valid until somebody asks a sensible question.", 42, 766);
            g.drawString("Issued on " + permit.date + " · Valid until a responsible adult asks one reasonable question.", 42, 766);
            g.dispose();
        }

        private void centered(Graphics2D g, String text, int centerX, int y, Font font) {
            g.setFont(font);
            int width = g.getFontMetrics().stringWidth(text);
            g.drawString(text, centerX - width / 2, y);
        }

        private void drawWrappedCentered(Graphics2D g, String text, int centerX, int y, int maxWidth, Font font, int lineHeight) {
            g.setFont(font);
            List<String> lines = wrap(g, text, maxWidth);
            for (String line : lines) {
                centered(g, line, centerX, y, font);
                y += lineHeight;
            }
        }

        private void drawWrapped(Graphics2D g, String text, int x, int y, int maxWidth, int lineHeight) {
            List<String> lines = wrap(g, text, maxWidth);
            for (String line : lines) {
                g.drawString(line, x, y);
                y += lineHeight;
            }
        }

        private List<String> wrap(Graphics2D g, String text, int maxWidth) {
            List<String> lines = new ArrayList<>();
            StringBuilder line = new StringBuilder();
            for (String word : text.split("\\s+")) {
                String candidate = line.isEmpty() ? word : line + " " + word;
                if (g.getFontMetrics().stringWidth(candidate) > maxWidth && !line.isEmpty()) {
                    lines.add(line.toString());
                    line = new StringBuilder(word);
                } else {
                    line = new StringBuilder(candidate);
                }
            }
            if (!line.isEmpty()) {
                lines.add(line.toString());
            }
            return lines;
        }
    }
}