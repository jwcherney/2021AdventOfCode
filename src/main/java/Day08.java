import java.util.ArrayList;

public class Day08 {

    public static void main(String[] args) {
        Day08 day08 = new Day08(input);
        System.out.println("Part1 Count: " + day08.getPart1Count());
        System.out.println("Part2 Sum: " + day08.getPart2Sum());
        /*
        Part1 Count: 521
        Part2 Sum: 1016804
         */
    }

    String[] inputLines, outputLines;
    ArrayList<String> input2, input3, input4, input5, input6, input7;

    public Day08(String input) {
        String[] lines = input.trim().split("\n");
        inputLines = new String[lines.length];
        outputLines = new String[lines.length];
        for(int i = 0; i < lines.length; i++) {
            String[] inputOutput = lines[i].trim().split("\\|");
            inputLines[i] = inputOutput[0].trim();
            outputLines[i] = inputOutput[1].trim();
        }
    }

    public int getPart1Count() {
        int part1Count = 0;
        for (int i = 0; i < outputLines.length; i++) {
            String[] outputElements = outputLines[i].trim().split(" ");
            for (int j = 0; j < outputElements.length; j++) {
                int outputElementLength = outputElements[j].trim().length();
                if ((outputElementLength == LENGTH_1)
                        || (outputElementLength == LENGTH_7)
                        || (outputElementLength == LENGTH_4)
                        || (outputElementLength == LENGTH_8)
                ) {
                    part1Count++;
                }
            }
        }
        return part1Count;
    }

    public int getPart2Sum() {
        int part2Sum = 0;
        for (int i = 0; i < inputLines.length; i++) {
            part2Sum += getPart2Value(inputLines[i], outputLines[i]);
        }
        return part2Sum;
    }

    int getPart2Value(String inputNumbers, String outputNumbers) {
        init();
        String[] inputNumberStrings = inputNumbers.trim().split(" ");
        String[] outputNumberStrings = outputNumbers.trim().split(" ");
        groupInputNumberStringsBySize(inputNumberStrings);
        String one = input2.get(0);
        String four = input4.get(0);
        String seven = input3.get(0);
        String eight = input7.get(0);
        String segments_adg = getCommonSegments(input5.toArray(new String[input5.size()]));
        String segments_abfg = getCommonSegments(input6.toArray(new String[input6.size()]));
        Day08.SevenSegmentDisplay display = new Day08.SevenSegmentDisplay();
        String segment_a = display.activateSegments(seven).deactivateSegments(one).toSegments();
        display.resetSegments();
        String segment_e = display.activateSegments(eight).deactivateSegments(four + segments_adg).toSegments();
        display.resetSegments();
        String segment_g = display.activateSegments(segments_abfg).deactivateSegments(four + segment_a).toSegments();
        display.resetSegments();
        String segment_d = display.activateSegments(segments_adg).deactivateSegments(segment_a + segment_g).toSegments();
        display.resetSegments();
        String segment_b = display.activateSegments(four).deactivateSegments(one + segment_d).toSegments();
        display.resetSegments();
        String segment_f = display.activateSegments(segments_abfg).deactivateSegments(segment_a + segment_b + segment_g).toSegments();
        display.resetSegments();
        String segment_c = display.activateSegments(one).deactivateSegments(segment_f).toSegments();
        String segments = segment_a + segment_b + segment_c + segment_d + segment_e + segment_f + segment_g;
        display = new Day08.SevenSegmentDisplay(segments);
        int returnValue = 0;
        for(String digitString : outputNumberStrings) {
            returnValue = 10*returnValue + display.toDigit(digitString);
        }
        return returnValue;
    }

    void init() {
        input2 = new ArrayList<>();
        input3 = new ArrayList<>();
        input4 = new ArrayList<>();
        input5 = new ArrayList<>();
        input6 = new ArrayList<>();
        input7 = new ArrayList<>();
    }

    void groupInputNumberStringsBySize(String[] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            switch (inputs[i].length()) {
                case LENGTH_1:
                    input2.add(inputs[i]);
                    break;
                case LENGTH_7:
                    input3.add(inputs[i]);
                    break;
                case LENGTH_4:
                    input4.add(inputs[i]);
                    break;
                case LENGTH_235:
                    input5.add(inputs[i]);
                    break;
                case LENGTH_069:
                    input6.add(inputs[i]);
                    break;
                case LENGTH_8:
                    input7.add(inputs[i]);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + inputs[i].length());
            }
        }
    }

    String getCommonSegments(String[] segments) {
        if (segments.length > 0) {
            String returnValue = "abcdefg";
            for (int i = 0; i < segments.length; i++) {
                returnValue = getCommonSegments(returnValue, segments[i]);
            }
            return returnValue;
        } else {
            return "";
        }
    }

    String getCommonSegments(String a, String b) {
        String returnValue = "";
        String[] letters = (a + b).split("");
        for (int i = 0; i < letters.length; i++) {
            String letter = letters[i];
            if (a.contains(letter) && b.contains(letter) && !returnValue.contains(letter)) {
                returnValue += letter;
            }
        }
        return returnValue;
    }

    public static class SevenSegmentDisplay {
        String setting;
        String strSegments;

        public SevenSegmentDisplay() {
            this("abcdefg");
        }

        public SevenSegmentDisplay(String setting) {
            if (setting.length() != 7) {
                throw new IllegalStateException("Invalid SevenSegmentDisplay input: " + setting);
            }
            this.setting = setting;
            resetSegments();
        }

        public int toDigit(String segmentString) {
            resetSegments();
            activateSegments(segmentString);
            return toDigit();
        }

        public int toDigit() {
            int number = -1;
            int intSegments = toIntSegments();
            for (int i = 0; i < NUMBERS.length; i++) {
                if (intSegments == NUMBERS[i]) {
                    number = i;
                    break;
                }
            }
            if (number == -1) {
                throw new IllegalStateException("Invalid. Specified segments don't convert into a number");
            }
            return number;
        }

        public String toSegments() {
            return strSegments;
        }

        public SevenSegmentDisplay resetSegments() {
            strSegments = "";
            return this;
        }

        public SevenSegmentDisplay activateSegments(String segmentString) {
            actOnSegments(segmentString, true);
            return this;
        }

        public SevenSegmentDisplay deactivateSegments(String segmentString) {
            actOnSegments(segmentString, false);
            return this;
        }

        private void actOnSegments(String inputSegmentString, boolean turnOn) {
            String[] inputSegments = inputSegmentString.split("");
            for(String inputSegment : inputSegments) {
                if (!strSegments.contains(inputSegment) && turnOn) {
                    strSegments += inputSegment;
                }
                if (strSegments.contains(inputSegment) && !turnOn) {
                    strSegments = strSegments.replace(inputSegment, "");
                }
            }
        }
        private int toIntSegments() {
            int returnValue = 0;
            String[] settingSegments = setting.split("");
            for(int i = 0; i < settingSegments.length; i++) {
                if(strSegments.contains(settingSegments[i])) {
                    returnValue |= SEGMENTS[i];
                }
            }
            return returnValue;
        }

        static final int SEGMENT_A = 0b1000000;
        static final int SEGMENT_B = 0b0100000;
        static final int SEGMENT_C = 0b0010000;
        static final int SEGMENT_D = 0b0001000;
        static final int SEGMENT_E = 0b0000100;
        static final int SEGMENT_F = 0b0000010;
        static final int SEGMENT_G = 0b0000001;
        static final int NUMBER_0 = SEGMENT_A | SEGMENT_B | SEGMENT_C | SEGMENT_E | SEGMENT_F | SEGMENT_G;
        static final int NUMBER_1 = SEGMENT_C | SEGMENT_F;
        static final int NUMBER_2 = SEGMENT_A | SEGMENT_C | SEGMENT_D | SEGMENT_E | SEGMENT_G;
        static final int NUMBER_3 = SEGMENT_A | SEGMENT_C | SEGMENT_D | SEGMENT_F | SEGMENT_G;
        static final int NUMBER_4 = SEGMENT_B | SEGMENT_C | SEGMENT_D | SEGMENT_F;
        static final int NUMBER_5 = SEGMENT_A | SEGMENT_B | SEGMENT_D | SEGMENT_F | SEGMENT_G;
        static final int NUMBER_6 = SEGMENT_A | SEGMENT_B | SEGMENT_D | SEGMENT_E | SEGMENT_F | SEGMENT_G;
        static final int NUMBER_7 = SEGMENT_A | SEGMENT_C | SEGMENT_F;
        static final int NUMBER_8 = SEGMENT_A | SEGMENT_B | SEGMENT_C | SEGMENT_D | SEGMENT_E | SEGMENT_F | SEGMENT_G;
        static final int NUMBER_9 = SEGMENT_A | SEGMENT_B | SEGMENT_C | SEGMENT_D | SEGMENT_F | SEGMENT_G;
        final int[] SEGMENTS = new int[]{SEGMENT_A, SEGMENT_B, SEGMENT_C, SEGMENT_D, SEGMENT_E, SEGMENT_F, SEGMENT_G};
        final int[] NUMBERS = new int[]{NUMBER_0, NUMBER_1, NUMBER_2, NUMBER_3, NUMBER_4, NUMBER_5, NUMBER_6, NUMBER_7, NUMBER_8, NUMBER_9};
    }

    static final int LENGTH_1 = 2;
    static final int LENGTH_7 = 3;
    static final int LENGTH_4 = 4;
    static final int LENGTH_8 = 7;
    static final int LENGTH_235 = 5;
    static final int LENGTH_069 = 6;
    static final String input =
        "gfbd geadcb gaefc agdcf cdgfba dcf abdefc cadbg agfecbd df | acgbd dafcg fd df\n" +
        "fdcbge dgfeca eagcd cdb bfcag acdebg dcagb ebgdfca abde db | bd dcb agdcb fbegdc\n" +
        "gdabcf gcfdae dagecb acfdgbe fabge fcbd cb adfgc acfgb bca | dagbcef fedgac fcadegb bca\n" +
        "febag gefbc acfegbd fdega ba adbe fecdga ebagfd gcbfad gba | ebda gabfced baed bfgec\n" +
        "afebg bgaedfc degfa bga fdgbea deafcg ab gcbfe dgcbae bfad | dfgae ba faged agefcdb\n" +
        "dfgebc cfdgbea adc ad fdcba acgbf fdae degcba efcabd decbf | feda ad decbga cdgefb\n" +
        "afce aegdbfc bdgeca def ef ecafgd gedca cdegf cbfgd afegbd | dcaegbf ef acfebgd bedgfa\n" +
        "fcgeb gabefdc cgda fdc gdfbae deacfb fcaegd fedga dc gecdf | fdc cdafbe dc cd\n" +
        "be acgfdbe bceaf facedg dafebg acfegb cegaf bfe bceg fbdac | fbe abfcdeg cadbf ebacf\n" +
        "egbfd da bdca edgab gcbea dga eabcgf dcgbea afgcde dfabegc | ad ceafdg da gad\n" +
        "cfdabge bfdac dacfeb gcedbf fbdgac gbd gd acdg dgafb bgafe | dbg dbg fdacb cbdaf\n" +
        "fbdeacg faegcd cdfgba geafc bga afebg ab acfbeg baec ebfgd | ba agb baec ab\n" +
        "gafd bcaef fg gaefb gcadbe agdbe egdbfc gebfad bfg gfdecba | fdaebg abdcfeg fgb faebcgd\n" +
        "efgdc caefg fga gecba cadgbe facgbd aebf fcaegdb fa agcbfe | af gbdfac bgdecaf af\n" +
        "gdfe afdec fd edfacgb agcbef cefga adcbgf fda acdeb edcfag | bcdfga agcfed cfdgba fdecag\n" +
        "cfgbdea dcaebf cdg ebdfgc gd ecdfga bcefd agfbc gbde fgdbc | cefdb cfagde gd dfceag\n" +
        "ecfad cdafg ced febad ebgacdf dfebcg badfeg ec abce beacfd | gefbdc bgfaecd abgcdfe degacfb\n" +
        "deg agcd dg cfead febgc cbfgaed ebfacd faegdc gfdce fdgbea | dg gde fbcge aecdgf\n" +
        "aed ae gdcae efbadc fcebgd gdcbfea cgbeda abeg dgacf cgbed | ae ae agbe gcbed\n" +
        "ebaf cbged ae bgadf efagdb edgab ade bgdfaec dgbfac gefadc | dbgce adbegcf abfgcd ae\n" +
        "bgfca dfgcbe bgdeca gfdbea efdgbca fd cfbdg fced fdb egbcd | ebgcda dbf gcbaf dcgfb\n" +
        "fgeacb dae dcegab debfac gabdf fecd ebadf fgbeacd de baecf | cbgaedf faecdgb cgdafeb ade\n" +
        "gafdcbe fce defagc caeg adbegf cfbgd gfdec fedga decabf ec | ec acfbgde acgbdfe befadgc\n" +
        "acdgfb cafgd dgcba cb cab gfdcbea bcfg fgcdae bgade fdabec | bc deacgf bac cfgb\n" +
        "dafcg dcbega gdbcfe dgfcb bf gfcabe fbed gbf begdcaf gdcbe | bdeagc fb dbfe fbg\n" +
        "gaedfc cdfbg efbcd fed acbed dgbcae bfae ef befcdag abcdfe | fgebdca fecdb fed fedbgac\n" +
        "df cedf gfcdb badgfe ecbadfg fdb ebdcgf gbdca cbfage febcg | fcebg gbacd fd fd\n" +
        "dbfce faebd dgefba cfgbe dcab acbdfge adfceg dc cfd fadbce | bacd cedfagb acdb fdabe\n" +
        "fedbcg adcegb dgbec gabfc dafgecb eadbfc bfd fedg gbfdc df | fged fbdcae dcbfg efdg\n" +
        "eafcdb acgf ecgfd dfgbe gec bdeacg dfegcab cg dfcaeg eadcf | dbfeg gc fdeac ecg\n" +
        "agbcedf cafbeg gdabf fagcd afegc cdeg dfc dc cefadg dfebca | dc cd dfc afbcedg\n" +
        "cfadbge ag bdfea cegdbf cefgda afcegb fag afdeg gcdfe gcad | ga dfgcae dagcef agcd\n" +
        "cdf abfdc afcgbd fd ecbgafd gfda cbfage adecb gcdbfe gafcb | cafgb aecdfgb dfgbcea gafd\n" +
        "bcgeadf bacgde dca da fedgac dfgec cfgdeb fedca gfda cfbea | fegcbd bgdface dca geacbdf\n" +
        "bedcg ageb gde dacegb acgfed bcedf agbdc cfgdba ge begdfac | efbdcga dbacge acbgd egba\n" +
        "de ced gcabdfe dgacef egdf gaecf cgbeda dbcaf facde gbface | gfed cbedag abefdcg gecbafd\n" +
        "gfbc ecbfdga dbcae cafegd cfa cf fbadg bgadef dfbac fcgadb | eagbdf dafcbg cf fgdab\n" +
        "bcedaf cgbdfa dgafeb ca bfacged bagdf egdcf agcb gdfca cda | debgfa cad agfedb acd\n" +
        "fe eagdcb efcdab bef fcabe agcbf cbefadg gbfced cbdae aefd | fe dacbfge bgfac fe\n" +
        "adfgb eagbfc gbdea fbgead abced egfd dcgfeab dfgcba eg eag | gdefabc aebcd ge cdfgabe\n" +
        "gbfad abecdg bedafc cfa ecfd dbcaf cafebg ecdab fc cdbfage | acgfbed cedf gabced cf\n" +
        "cgfda eafdb dcgaeb bgef egd ge bcaefd adgcfeb defag bafdge | edbcafg ebgf eg afdge\n" +
        "fdb fbegcd bgfad abfgdc efadcgb fbac fdecga agedb gfcda bf | afdbcg abged egbad bfca\n" +
        "edbagf eg debgf agdecfb gaed gdcfb fadeb gfe deafbc fgabce | afcedbg eg degbcfa fcbdea\n" +
        "bce badcg gedcbaf begacf agdcbe egdfb edgcb deca gfcdba ec | gcfebad ec cbadg ec\n" +
        "fbagcd ebdcf caebdf bdeacfg aecf fc dabegc cbead fcd edgfb | ebfgadc bgacdfe gdafbc cdbgea\n" +
        "bgfac gdac dg cfebdg becgfad bdg bdfcag edfab fadbg ebcfga | cdbfeg dcgbafe bdg badfecg\n" +
        "gde bcgda cagefd ge agdebcf dfgbae gebda defbca bfade febg | befg fgeb baedf edgfab\n" +
        "gab fedba agfcebd dcafg egafcd bg fdcabg bfdag cedgba cfgb | dfbgca acfbgde adgbf bag\n" +
        "af afg cbfa abgec cdgeba bfedcga faegcb gdfcae gebfd bgaef | gfbea egafb fa egcfab\n" +
        "dgcfe ca bcdafg adecg dac bgdecf fdceag cfea faecgbd aedbg | ca acd debgafc ca\n" +
        "egadcfb ea eac eagcfb bgdcaf cfedg baed becgad eacdg gdbca | abed ace edba aedb\n" +
        "aedbgf dfgcbe fcda fadbg agbec agcbdef fc gbfca fgdabc fcg | cf gbcafd gfc gbadcf\n" +
        "dfge adcebfg caebdg dg bfgcd bcegf dcg gaebfc adfcb cgedfb | gabced cgd bgfcdea dgbcf\n" +
        "baef gcabefd bge dgcabf agdce baged badfge dgbaf eb cdgbfe | abfe ebg fdbgaec aefdcbg\n" +
        "eagbcd gbd gbcedaf bacfd afbg fgcbd cafdeb bfcgda gcfde gb | gfdcb bg gdcfe afgb\n" +
        "baged bdaec eabcfd cbe efcbgd ce fbadcg cegdafb fdcba aefc | ceb aecdgbf bcdaf bdgea\n" +
        "bcaf becfd eac gdaeb caefbd cabde bedcfg fedgac ac dabfegc | ca fbac bcfdage bafc\n" +
        "efc ce deacf ecga acdfg gcbadf defab fdbecg eagdcf gbefcda | ce bgafdec cage aceg\n" +
        "cgf cebfgd bgced daegcb bgcef abfeg fc egfbacd dcbf dcgefa | agdecbf gcf abcedfg dcgbe\n" +
        "cgfde bgdeaf ebcd cfe efgbd ce afgebc cgafd efdcbg edgabfc | dcgef fgcabe fgbdeca abfegc\n" +
        "gecad be gadbf fdbgea dgcfba ebcgfa geb febd gcadbfe gedab | fedb fbdga eb eb\n" +
        "gebafcd ea aced badfc bafde cdgbfa geacfb egbdf fea fbaecd | aedc gcadbef bgacfe fae\n" +
        "cdaeb bgafed bacegd adc daebg efbca dbcg fgadce dc cdgafeb | dca gfdeac gbcd bdeafcg\n" +
        "aefgd ce dfcaeb cgdfbae gebcfa gcbdaf fce bdec ceadf acdfb | cfeda ce gbafecd aegfd\n" +
        "bedfa fce ecfba ebcfag bedcag fc cagf geabc febdgc adcgefb | cagbef cef gcdebf fec\n" +
        "cdegba fe bacdfe bfgdae cdgebfa edbac fed fecb fagcd fedca | def cabdef cfeda bgfaecd\n" +
        "edfcb dcbg ecadfb agcdef afegb gfd gd dfgbe ecdfgab cfbgde | bgcd cdeabf dgf fgd\n" +
        "daceg fecdag dfbca gb bfcegd bgc afbgdec adbgc geab gcaebd | gdacb agbe cfebdg bg\n" +
        "agfbec deagbfc cbgda adbce deag dfacbe fdgcb dcgbea bga ag | adbgcef decabf cgbfd cabed\n" +
        "bgdf df bgceda aedbgf abgedfc cgafe adbge gfade bdcfae fde | gbdf geacf baedg cbfead\n" +
        "beacdf egfa afecd ga gca gbdec cagfde cdfabg aegcd cdfbgae | bgcde egbcd cdega dcbeg\n" +
        "fbc abfedc bagce fb fcgdea cdgabfe bdgacf bdfg cfbga cdfag | acfbg egacb dfacg fcb\n" +
        "dgbafe efdgbc gdc deafc bfgcad cg bdefg defgacb ebcg egfcd | bcfgda gfbead cdg dfgebac\n" +
        "aceb bdfega bdfgc bdage ec dcfgeab cdagfe bagcde ecg ebdgc | ebac bgaed gec ec\n" +
        "cfbega ab dgebca fgacbed bcgedf fgab abcef fcade fcebg acb | fadcebg cab abefc dcfgeba\n" +
        "deacbf fgeab ecdbgf bad fdbgcea eagdb cebagd dagc dbgec ad | gbcdae agdeb dcabef gacd\n" +
        "gefcda dga aedbc afbegd edgab fgab gedbfc cfebadg gdefb ga | ag afbg becda dag\n" +
        "dbfcge baedc ebg efag dgbaef agfbd fagcbde fdagbc ge geadb | eg adefgb bfdgce gdfceab\n" +
        "ebcfgda cg cgfe dfgeac fdcabg agedc agfed dgc adcbe bgafde | aebfcdg agdef gefadbc dagefc\n" +
        "dagef gedafcb ecafd cgfabe dg efgadb dbga dge efagb cgdfeb | dbfgce fdaec eabcfg dgba\n" +
        "acbdf dbfagc dabgc bgedca bcdfeg cf bfc efbgacd gcfa debaf | ecdabgf cbf fcbaged fcbgde\n" +
        "bdfega aecbf cadf gecbf cae dfabe ac gdacbe febcgda dbecfa | fgbade ca adbef dcfa\n" +
        "cbdaef ebadf gdbefa eafg aefcgdb gab bacged fdgab ga bgdfc | ga cfgadeb dabgce bag\n" +
        "gd aedcfg cadfe dgfbec adegf eafbg gdac bfcaedg ecadbf fdg | fdacge gd gefad egadbcf\n" +
        "dfaeg bafged fdgec adefgc ebcgf edc bdeacg cd cfdgeba afdc | bcgdefa caegbd cd adegf\n" +
        "cfd cadbf gcadbf cbgd defgba dcegaf egbfacd dc badfg fbace | afcgde afbgdce adbfc dc\n" +
        "fgbed fcaeg fbgcda cb cbg efabgd bdfceg debc cabedgf egfcb | gbdcfe edcb aefcbgd edcfgab\n" +
        "efgabdc fcdba gbcaf fgdbae ag dgac ebfcg edbacf agb dgcabf | bag ag ga cdga\n" +
        "dcbfa bfcaegd fda fabg acdbe gcfedb faecgd gfbadc fa bcfgd | dbcgfa dfa afd fdbca\n" +
        "aec bgdaec gbdca efdgbac ecgad ecfdg eabfcd cbgadf ea bega | ace ae efbadc eagbcdf\n" +
        "fgca dgfeb bacedf beadfcg adf cbgad gabfd gdfcba af agdcbe | daf facg facg fcga\n" +
        "becgfda fgae geafdc dagcfb aecdf ecf fe befgcd ebcad cadgf | gfea geaf fgbdcae adbgcf\n" +
        "dcebag agdce cafbedg eda adebfc cfedg dgcab cdagbf ea abeg | ead gdcbae ea dae\n" +
        "gaebfd abgefcd fgeadc bgedca cbdgf ceaf gdcef efd ef dgace | geadbcf edf aecf afec\n" +
        "afd beadfc facgeb da dgcfba dabefgc ebfdg gdabf dcag fbgca | ad cdga afd efdgb\n" +
        "abdge agfcebd feabcg dgbef bgacdf bdcfeg fe bfdgc bfe cefd | fcde efb gbdcf ef\n" +
        "gdbcf cafbd ad gbad aebcf cbfegd gdcafeb fegdca adc bgdfac | efbdgac abdg gafedbc acd\n" +
        "gdea fdaecb cda fgdec gcbfa eacgfd gcefdb da gaebcfd adcgf | da ad aebcdf ebadfcg\n" +
        "faebdcg bagfc debcag acgef fgbace abc dfacge bgdfa bc cbef | cgefa cab bcef cb\n" +
        "fgaceb ea afe gcaefd dfbecg bage dbfca fbeac gebcf daecfgb | aef efa cgebfad efcbgad\n" +
        "cfgde aegcbf fce fabdcg edfb cgebdfa dcgfeb ef gdeca fdcbg | fe ef ef ecagbfd\n" +
        "agebcd fecgabd facg dcbfe agefb cebgfa aec badefg ac cbaef | ac cgaf ac begfa\n" +
        "cfebag bea aedcf eb ecfbad adfgec cgbad dfeb dfbaceg adceb | dbef eb efdcgab eb\n" +
        "gdfcba efadcg fdcga bdfae cgbd gb afbgd gebdcaf agfbec bfg | fgb bcgeaf bg gfb\n" +
        "eafbd ceafbg dgb caebfgd cgbfe fgcd acbedg ebdfgc dg ebfgd | fcdg cfgd gbd dbg\n" +
        "gbfce fag aedg ag gafec bcgfda efdca gfdaceb fdegac cedfba | bgdafce cbdefga aefcg fagec\n" +
        "cedfa fgcbe acfedb ag cegfa dacfge dgaebc acg afbecgd gafd | gadf cga gdfeca degcab\n" +
        "cegfb gf dgefbc cbfade dfcg fbg cedbf bfgdae fcdgabe aecgb | gbf bfg efdbc efgabd\n" +
        "acdeg fdbae gcebdfa efgcba cdgb ebg gdabe acgebd gb fcdgea | bg geb beg afgdce\n" +
        "bd cbgedf ceafgb agecb bdac daefg befcdag ebd cdaegb dbage | aebcgf bde bed gceab\n" +
        "ad adbg beafc dbcgf gfebacd fbdac dgacfb gecdfb adf cedafg | gdab beacgdf daf da\n" +
        "adcbge febadg dbgcf bace eb edcga ebg gfcdabe egafcd gbdce | gbe defacg cdfbg geb\n" +
        "dfgea fgceb ac egcadf aegfc deac cag bfeagd bfdgca ebafgcd | dgbaef gcfae bdefag gcafe\n" +
        "fagce dga feadg fgcdbe acdbegf bafd da gbfed bgcdae bfagde | da dga dag fdab\n" +
        "dafbe fdcabe cafbd cfde dc cabgf edagcb acd bcgfade gbeafd | adc dfaecgb bgcaf cad\n" +
        "acdgf adgcfe bg fgb gbdcaf fbagc dgbeaf gcbd faedbcg efbca | bg gb cdgb abdgcef\n" +
        "adbecf gcfdeba acdge dfab befdc bagfce af dcfae fca bedfgc | acf fa cfa cfa\n" +
        "eabgcf agc cafgdb adgbf dcfa edcgb ac bdegaf abgcd gedfcab | ca fcegab bgcdfa acebdgf\n" +
        "gcbfde bg gdba bafeg bafde cfaeg cfbead gbe fgebad abecgfd | beg fdgaebc bge cebgfd\n" +
        "ad fadcb fbdce acd faed dbfegc cbdafe ecbdafg decbga cbafg | da gadecb bcfdae cad\n" +
        "dcfgea defbg becgdaf eadb edafgb be gafde feb fdbcg gfebca | eb afdegbc bef eb\n" +
        "egcdb ae fbdeac bgace fabgc edgbcf eac bacfegd eadcgb geda | fcdeba gdea ace ea\n" +
        "befd dcgeaf cbdage bgfceda fbcag be daegf agbfe febadg bea | eb edafgbc acbged ebfga\n" +
        "dbfgcea abfecg bga dgca ecabd ebcfda gdbef ag bedga gadbec | agcd befdg bga cfedba\n" +
        "da agfde fbega fda bgdeafc cgad cdfgbe efgcad afedbc decgf | cdgbfea dagc ad daf\n" +
        "bfcadg aecgfb fgade cbafg fdcgaeb db bdg agfbd adbc bfdgce | dfage bd bacd gafbd\n" +
        "bcdfge eadfg fcda dfegcab fdbgea cfg egfca fdgace baegc fc | fcebadg edafcgb fgc fc\n" +
        "fbdac gadbfc fedbca bfage feabd ed eda aebgdc feadcbg ecfd | ed efdc dcef fecadbg\n" +
        "abdge abgdce fg efdga dgeafb dgfb fge ceafd bdefgca fagebc | aebdg feg dbfg cedaf\n" +
        "dgcf caefb fceadgb fd cdbfe fgbced begdfa cdgbe agbedc dbf | dbf bface edcbag dbacgef\n" +
        "bgafe bf gbfcad gadef egfdba baegdfc bedf fgb becag defcga | dfbe dfagec cegdfab bf\n" +
        "ed cgfaebd edbgca dgabe dcbagf dbgcef agfbe dacgb egd aced | dge egd degab abcgfd\n" +
        "bfgeca edcbg deabgf afdge adebcgf fb fgbde fgb adfb fcaged | bafd afcgbe gbf bf\n" +
        "bg afcdb gedfc afecbg cdbeagf dgeb cegafd gbfdc gfb cfedbg | gb gb gfdcea cfbgd\n" +
        "gf fbgc gbcea gef abgecd efcag fdcea gfcaeb gebfdac fgbeda | gebcda degfbca gef cfgea\n" +
        "fadebcg dfecag dcbaf cgdfb bg cfgbde fcged bgc gefb dgcbae | acgbde cgb fbge becgadf\n" +
        "gdba fbcgea dcaegb abecg eadbcf ad afcedgb agcde fcedg acd | cageb dac adgce adc\n" +
        "ebdca fd dcfbe efbcad afdb ecbgda aecdfg fdc cbgef efdcbag | dbaf df cbadgfe bdaf\n" +
        "dgcbafe ef gaefcb bfgead ecfag gadec gcabf gfbdca fceb afe | afbcg ef dagec cgeaf\n" +
        "abegc badgce gecfdb gb dagec gdab dcgafe dgfabce ceabf geb | gb dcagbe cbaegfd acefdg\n" +
        "adec cbgde cdg egbdaf edabgfc adegb cd cegfb gdceab cfbdga | dc cd cd dcae\n" +
        "fbadcg abc ba cfbge abed afgecd aecbg adcegb gaecd fgbdeac | acdbgf afdbegc bgafdce acb\n" +
        "fa febgc cgfead ebgcdaf abged beafcg begaf aef ecgdfb bafc | fgbea acbf gbfeac afcgdbe\n" +
        "bcadg baced gbfcde ec aegc cgedfab dec aedbf dbacgf abcedg | adbcg ce fagdcb adbcfg\n" +
        "fdgcba acdg befacd bcg bgdef gbceaf gedafbc dfcba dgcfb gc | facdb gbc bdfeac gdfcbae\n" +
        "cd gdce aedcfb fabecgd gdacfe cad adgcf gfbac gbafde deafg | dac cdbgefa cd dfcgeab\n" +
        "aebdfg fgcadbe gcafbd eadfb ea abfgd bedagc eba fecbd eafg | cebfd dcbgeaf becgad ae\n" +
        "edagfc cagdbef eadbfc ga fagec gdae efbgc cag gdcabf feacd | acg cga cgfdea ga\n" +
        "agebdc bagde efg ecfadgb gbfeac fdage fg bfdg geabdf efacd | afgedb cbegfa adbegc fg\n" +
        "dgcfb gacbed bgaefcd eb defac bfae bdecaf cbedf fgdeac cbe | eb ecb gbdcf egdcba\n" +
        "bg dcegbaf bcafeg aecdgf gbf gfecdb gaeb eacgf fdcba cafbg | bfgdec gbf aebg bg\n" +
        "aeg abfdcg gefcdba bgfe bcgaf edafc aecgf ecabgd cbafge ge | age acgbdf eag egabdc\n" +
        "bafgde gefbcd caegfdb cdb fbcad gbfad cb dfcae fbacgd cbag | gcab afcdb bc cdbgaf\n" +
        "cbgdf fda ad acgd agbdf dfaceb bcdfge agfcbd efgbacd gafbe | dfa dfgbec ad fad\n" +
        "gfdca edafgc ge fbedcag ebfgad dbacgf beadc gfce eag gecda | gacde cegf acgbdf cbgadf\n" +
        "cdb dfgec dacefb cfdeb cbaefg ecbadg bafd afceb bd abdcefg | ecbadf bcd bd afceb\n" +
        "gbed be cdfbga bef ecbdfa gdbaf bdfage bgefa fcgae gefabdc | fbagecd gdfab gbdfa gdbe\n" +
        "dafeb gfedb bcegf dg edagbf dagcfe fdg gebdacf dcfbea gdba | gfd bgad gd febgad\n" +
        "gbcdaf aedfcbg dcge dcagf gecfda fde fgdbae fabce eadfc de | decg ecdg gdec de\n" +
        "bfcga cfeg begdca ebcfag bdacef aecbg fac fc fbgda febcagd | efcg cf abdgec gfabcde\n" +
        "cgfb fca dfceag bcfae gcabdfe begaf cf befacg acedb afdegb | egdbfa acf acbed fc\n" +
        "bedgfc ab fabe acfgd cgbdea fdacb fdeabcg bac fbcde edacfb | bfae efcadb dfgca cba\n" +
        "bcgeadf cfadg fge bdacfg eacfg bcedfg ef edfa cbgea cdefga | ef ebafgdc efcgbad adef\n" +
        "dfgec fcdgaeb befc fb afdegc bgcdf dgbaef gbf cgbad cebgfd | bfg fb cbfedag ebacdgf\n" +
        "ceabfdg dfe dfcg gedaf afgce ebdga egcbfa edafbc df gdaefc | abcedf ecfagbd gaecfbd gcfd\n" +
        "degfb fa fdeab acfb deacbf gdcbea fad bacde efacdg bfdcega | adf dbace gfedcba gcebda\n" +
        "badegfc fcebg fd fbd fdeg afcdeb gfeabc fgcedb bdagc cdbgf | dbf dbf fdb fedg\n" +
        "eabcfg cefbdag cgebfd cafge deac gde adbgf decgfa de edafg | fdage adce de de\n" +
        "acbdeg cdfgba gac dbega adfbge cdeg acbgdef gaebc cg afebc | dcgfba acg ebagfd bdecagf\n" +
        "bfadcg bgcda ega gdcef gdbecaf ea edba egadc gebcfa gadbec | ae bade gdcaebf eadb\n" +
        "fe efcagbd cfgbd efdb gef gfced decga gbcedf gdfbca aebfgc | ceadfgb dgbacf cgbdefa egf\n" +
        "gd fbcgda gad gbdfa dfcg ceadbf acgbdef ecgbad faegb bdfac | gfcdeba dg abfcd fbgae\n" +
        "becgad egc ce edfgabc caef bgfdea cebgf faebgc fdgcb gbafe | ceg febag abgfe ec\n" +
        "abdef acdfbe gbdcea egbcf abefg gedacfb ga adgf gae aebdfg | gfad ga agdf gea\n" +
        "befdcg adcf fcdegab debcf aegfb dae cadbef da bfdae bdecag | gbfeacd gdbefc ad ad\n" +
        "dabgcf fgbdeca gecfbd fcdge gfbe bdgfc dcefa eg bagdec egd | edg dge dge dge\n" +
        "afd dagbc ceagdf fa cadbgf fbga badgec fdeacgb fcebd dfcab | bcdgea fad fbecgda af\n" +
        "dfecga cagbde fcdaeb dfgc afegc cf fec bgaef edacg fcegdba | aebgf gceadbf ebgdac cbfeda\n" +
        "gceaf gcbe bfecga beagfd cabgfed gfcba fcadb fgb gdcaef bg | abcfd egcb begc cegabf\n" +
        "gcf adfebcg dcbeg fdgacb gfceab fg gafe acbef bcadef cgefb | bcegdfa cebdg fgc fgc\n" +
        "dgbafec caf af gfcea cbgea efcgd aefbcd gecfba bgfa aegcdb | caf cegdf af agbced\n" +
        "cgfd gd fedbc dbfaec fedbg adbgce bgefcd bgfae gbedafc gbd | ecabdfg cdgf gdcf bdacge\n" +
        "bgc bcdfa becgaf gc dbegf fdcabe dgbfc cagfbd cadg fdabgec | fdcgb cdag gc cdfba\n" +
        "fbgcda gdce bcg acdefb aebcfdg fegba cg cbefd bcfge fgbecd | becgf cgb bgc bgc\n" +
        "afebg dceab aefbdg edgba agcdbf gd adg edfcgab fged fbgeac | cbagfd cgadebf agecfb deacb\n" +
        "bagcdf bcafde gabe fdabe dbegaf ag dgeaf agd facdbge fedgc | dbface gad baeg agbe\n" +
        "df gabfc afgcbed edgf gcbdea bdf ebdfcg fbcdae cgdbf bgecd | ebgacd dgfe adbfgec gdbfc\n" +
        "gadcb cgfd fadbce gbd bfcda fcbagde cabeg cdagbf dg faebgd | gadcbef dfgc fgdc dbg\n" +
        "dcagb cdfeba feacd fgdeac cge ge dgfe bafcge agced gceabfd | deafcb fedg gdef gacbd\n" +
        "dc befgda dcgf gceba edc cbdge daecbf daefbcg ebgfd ebcgfd | edc baefcgd ceafbd dce\n" +
        "cfdab edag afdcebg gba ecdgb ag fecgba bgdca egdbcf aebdgc | bagdc bafdcge gcdafbe aedg\n" +
        "dgfecb bdfage abe defbc ae fabgecd efac fdbcea gbcad dcaeb | abe cefa gfedcb adecgfb\n" +
        "dcegba fd adfgce caefb adgfeb fagdecb gfdc dfeca gdeca fed | fd dfe efacd gdcbea\n" +
        "dafbec gbefd gcdfba edagcbf fegcba abfed efa fdcba dcea ae | fea afe fdaeb adce\n" +
        "af bacf gcaebd dfgeb ecfdga bdaef aebdc decabf afe cegabdf | af bafc fea af\n" +
        "cdefbg ce acef gce cbgad afdbge fedacg dceag efagd bgadcef | ec cfea dcgfbe feac\n" +
        "gfbcd afbgd fgbaed abg ba geadf gdcbaef dbceag afbe feagdc | ab bdacfeg ab bga\n" +
        "cfgea defcga feacbg cfabe gcbafd fbc gfbe baedfcg deabc fb | agcdfe bfge fdgabc ecgbfa\n" +
        "gbdcea gbdf fgecb fbcdage agefc bge fedbca efgbcd bg debcf | dcgfeb cgaef bcdfe gabfdec";
}
