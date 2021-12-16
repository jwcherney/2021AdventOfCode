public class Day15 {
    public static void main(String[] args) {
        Day15 fullMap = new Day15(input);
        System.out.println("Part 1 output: " + fullMap.calculateMinimumTotalRisk());
        Day15 smallMap = new Day15(input, false);
        System.out.println("Part 2 output: " + smallMap.calculateMinimumTotalRisk());
        /*
        Part 1 output: 707
        Part 2 output: 2948 (INCORRECT TOO HIGH)
         */
    }

    int[][] riskData;
    int[][] riskTotals;
    int rows, cols;

    public Day15(String input) {
        this(input, true);
    }

    public Day15(String input, boolean isFullMap) {
        String[] lines = input.split("\n");
        rows = lines.length;
        cols = 0;
        riskData = new int[rows][];
        for (int i = 0; i < rows; i++) {
            String[] values = lines[i].split("");
            if (cols == 0) {
                cols = values.length;
            } else if (cols != values.length) {
                throw new IllegalStateException("Invalid: differing row lengths: " + cols + " vs " + values.length);
            }
            if (rows != cols) {
                throw new IllegalStateException("Invalid: not a square map: " + rows + "x" + cols);
            }
            riskData[i] = new int[cols];
            for (int j = 0; j < cols; j++) {
                riskData[i][j] = Integer.parseInt(values[j]);
            }
        }
        riskTotals = new int[rows][cols];
        if (!isFullMap) {
            int newRows = rows * 5;
            int newCols = cols * 5;
            int[][] newRiskData = new int[newRows][newCols];
            for (int rr = 0; rr < 5; rr++) {
                int rowOffset = rr * rows;
                for (int cc = 0; cc < 5; cc++) {
                    int colOffset = cc * cols;
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < rows; j++) {
                            int newData = ((riskData[i][j] - 1 + rr + cc) % 9) + 1;
                            newRiskData[i + rowOffset][j + colOffset] = newData;
                        }
                    }
                }
            }
            rows = newRows;
            cols = newCols;
            riskData = newRiskData;
            riskTotals = new int[rows][cols];
        }
        populateRiskTotals();
    }

    void populateRiskTotals() {
        int diagonalCount = 2 * rows - 1;
        for (int i = 0; i < diagonalCount; i++) {
            int elementCount = i + 1;
            if (elementCount > rows) {
                elementCount = diagonalCount - elementCount + 1;
            }
            int first_col = Math.min(i, cols - 1);
            int first_row = i - first_col;
            for (int j = 0; j < elementCount; j++) {
                int col = first_col - j;
                int row = first_row + j;
                System.out.println(row + ", " + col + ", " + elementCount);
                calculateRiskTotals(row, col);
            }
        }
    }

    void calculateRiskTotals(int row, int col) {
        if (row == 0 && col == 0) {
            riskTotals[row][col] = riskData[row][col];
        } else {
            int above = Integer.MAX_VALUE;
            if (row - 1 >= 0) {
                above = riskTotals[row - 1][col];
            }
            int left = Integer.MAX_VALUE;
            if (col - 1 >= 0) {
                left = riskTotals[row][col - 1];
            }
            riskTotals[row][col] = riskData[row][col] + Math.min(above, left);
        }
    }

    public int calculateMinimumTotalRisk() {
        return riskTotals[rows - 1][cols - 1] - riskData[0][0];
    }

    public String riskDataToString() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                output.append(riskData[i][j]);
            }
            output.append("\n");
        }
        return output.toString().trim();
    }

    static String input =
        "9789322886192667713999597652972828489298193868271919348889871967497986926939727358997884891977956945\n" +
        "9575621188292129524798288991976158721494398519617999984474999997868129319496861412917819477517222482\n" +
        "9938913275417259737791399678879224917698488468316318299282237915962938999933957461848517689319918993\n" +
        "6898819915327728987327491997885637921422289298919576769959539336534321881489969978992746885471879592\n" +
        "4963829699986919956889489519982926639654739389197329476178919455415491716121629195271897874465699238\n" +
        "7358459987482499374188284983294988962868379719899196792789791988514891519159384799716915771892188772\n" +
        "4162976816197999866923655179698623198825997771374898895888688995597978939254648894172996962246752589\n" +
        "4754174539187552198894945898819942678918997467528919186594355687154781284635249685889588176818859696\n" +
        "8996173596867227456667518581831616799999492266944658199951948294524926177249269111896895615369781999\n" +
        "8868421997986196584169899116882959848195969889499988557297167817194899289669249419926759496383896936\n" +
        "8766312198688391687219976965952658989987391955888973238289978999597584182999949251913898388122717983\n" +
        "9865872899157182836174725589797516985795777779996195155961981992978878993593962619993256841239831969\n" +
        "2847916888887977827333791162828181922175251338882553289661987465829996427927298499834899993349316358\n" +
        "4128799189999839945879142849895911173991267839112757492892689797546298939975192872522218597238386677\n" +
        "9585118323995914874118598992628156918659625999436287179998991866111983411979256697899812697791967881\n" +
        "2473342747283342941733264869984197498194894991489748481499996795398444475152196888241522281626894818\n" +
        "1561862691919992916687399819748295696399563669895992891359724957979768929992959179576986455172379935\n" +
        "3998467328787688893523187967821894266257989382799269964938627183669755859989829273958849981975583367\n" +
        "7118317879356336827883991915511929877396594341198929692164979899399767779433996496389414999283963959\n" +
        "9929798518431988489419579991874963694977291523894984215378169641887486849846181923111661489799694812\n" +
        "3847189169431319987229245491499397937998684899791699944892888978789929881859199899197872662264887188\n" +
        "5488389573859565679575937415951929284197128741915769642169589177617223748982117933889929767689548821\n" +
        "9171186974899555199799766875597388821778897896585594678613461492294754749379514383374387859881919132\n" +
        "9993799999838787198579723527789779998977416969192127153267979828748599644535992857763231853972398675\n" +
        "4838878979151587219899658635619947475257926698162177737999698277796698776516459198889389477998877954\n" +
        "9694969874498869939999969359693755297499968637656888873843774872923971678318164568989254534823139689\n" +
        "4971299745998998432575868176491959769761439789655892519219549799565995987999112989456439196284224115\n" +
        "4791618952948656995611877155991954319953159536419113191696177181317635679789899963195975969478896249\n" +
        "9783971979499194217398199865681965969811136949539939138599776462928669375669829695944981195887891926\n" +
        "3799977466429424987314391795951211227591862997439627986189936161937177654563699978939931685356187987\n" +
        "6823696516688598871931916997769969862387752979795791968997646988894595978911681449976856958993742981\n" +
        "7999885483626899981492649998196881854879569889728953665481985551526741687451759115941297397935891379\n" +
        "4396987694648581839888761721925797113194868141599638859194882297121139799899599696911998139596545757\n" +
        "9999838799991395391874699398696599566192821979622994569189874884649879159954889852896754556629985996\n" +
        "9798949988457139753876587964419497438619937397457126184928712989788189594913292378788445357891865998\n" +
        "9817991657537912158692361995986959894983688295991939799998836766785592787726924343575958916161464336\n" +
        "9992694191565347111728865235816128989289181879979491697673188957998889455889699159921863518366134983\n" +
        "1869797499279187219252665857499699457717491299845235599999992799978886967721689293261639994842197811\n" +
        "9989681713619996829587965153346898676898923787989939873251264889249981319662399199929899862987744493\n" +
        "3194948382499946195889946369969896761196329273175115679992755379139339671997999831131199998992548994\n" +
        "5211629698837219787461221117764957485528929967864998772974232369791126929779661977939256861518467166\n" +
        "9999973919589929695481662779182791448924112125874947981464178566996568899712459395179162499776429595\n" +
        "9331926426386699912497871939582811385799639889198593391789848159471299498233999814929898375718949949\n" +
        "7449277975497894196993199991395311648839639232937497359843843999282481195699569586394694755818793642\n" +
        "9943799294875653992719597866988179199985768986287911798576229155459768198653251466881867799939616299\n" +
        "4193547462427989866749751721591991399485869745797161678847619151839898947425687965263494534149465419\n" +
        "4919699794778198379781432994967479489987131691989688473599795491844574979128696969854818821878739189\n" +
        "9295878989184761553811122272677979981377954267419158787944569993734217952579864698638273992646717799\n" +
        "7278825874811322425685278737989972389898693133766924924558994797498264989996942996194499556925927538\n" +
        "4498881887886855536682859686959175919946697413772698119469794288347485444859146621891393995891955998\n" +
        "8892657845495281519957929569299991938284478815388479746919417899686979657891988397798995393992899986\n" +
        "3783379973979985885886761929997867678884549369988596153859193333127187949994919226193951985291291919\n" +
        "6985198911291175579192256749987748534199843817988695322652271223754958572196576589834926279219597111\n" +
        "3922928997943924918549936762286655338879467699779639859393795791889593198241998496543974987398243991\n" +
        "8929317678617669689864729969387946898927786445976453862436317786929139225851464571798716194674738887\n" +
        "2471476789379519436582968239115176979812994894893846896896548662868698919715139784419917977835276824\n" +
        "9878967791819951797895189689499353811399198997769195818419899561852431142994717691217211915943453792\n" +
        "9389973399947159718993918842882579911889825771872156336724759935489391292938542123588751985798939677\n" +
        "2448598899978468958859951911915782215498992576788295424915987289326771699979239991786326967887468542\n" +
        "9123535211919769922289922451373919682934595116193816718682731618952365895489394989559367151953544499\n" +
        "6967586947765781965697226384792627711893611731998946128996788459279865691997283687919892848768829792\n" +
        "9788177197284689799913381271879949969923779629357419193769716988277791769994949594929779489982416696\n" +
        "9861995966299797968499999749985996798151948996268298174632395395886468128687538499576172694988889979\n" +
        "8928979399919361584296999849289399919169589922838169796676991399936276785693248986993729299994897623\n" +
        "5689969895996896253914133528998752937744249746999917259299829299528681995748299149257886988596786983\n" +
        "1967193498739999117199895865597287978499925789571699761354896526378919558429198669716449998196427998\n" +
        "9391497137161744919182659364949894239879829494365137779957816947592198118989699391379199888882919889\n" +
        "9929685991482887697888953728839239711236962998178964837392886341823919739989499638968989918992681458\n" +
        "2578478999637998689888637471745378252752825191984972518312891248592217999853688229878264169887998899\n" +
        "8965419817118765588617648889999698994128718229572872937499113369376167316929699236184851277524962531\n" +
        "9998468614969937663815871477822914389884719841149689881672314249799973995747595397919999977972969956\n" +
        "9878567811983574499969715985669789181864347373792996219975452435951439983867663786514763398878593999\n" +
        "1228931573117278998819596593716311947941985669882826182669567975179944189899568859794941382899863871\n" +
        "2285384949694119868584981749969973887389889353682199976959999199839419495879183988122488633889211991\n" +
        "9677482953541261437945179789917789993997691778772539999118859794999489669737892719679595698766872991\n" +
        "8179434995495979179271175936299889797685996876414692597195319924695437896499888588299589398484518695\n" +
        "4198229872889816191538829999594927698121198399895874911879168993429186214998496299799299639521929599\n" +
        "8729947613849122679698999699598843713981982219979892495959319926619766119119999728795556997426965187\n" +
        "9389886998385478912464721426949979224692525799392317783367775439919865399789986669892979599519191997\n" +
        "8699942336188884993884111198119989731592857783893941869883998421498859896112818213772688191672968699\n" +
        "3363477468188468516951915715776659223696191786969122857894956226954999693951439611599599956999899719\n" +
        "6296277818789523369187369889854446685486958693662914285878281915896799675435682358473933399859888548\n" +
        "9628933937855667286188374999882939396597993579939795567692991144419348325829198928645521399382419525\n" +
        "6695851194548639681185186991431991485976685352217864876621257746159158152461968593717371527698993969\n" +
        "7981198599338999998243439795927597938129899632696934359288253923816895955599818979393172919947228339\n" +
        "6481279327827428989262849197979659863915932854769897975496398496419527562369814475245973291798294942\n" +
        "9636777997488378544959539959397699634919999968999393587991993923482829984883598931637169963199994794\n" +
        "4679288544931956459722891579145815127671892931818596618869849115817198119845446979917295472599979697\n" +
        "1922964596596893198298917277794999689971259691962918157851979298521437599867938871715999864919985883\n" +
        "7192996794187961784971871195299919646768499239995897588789154849914439799941329442854599177168989961\n" +
        "2883583771638187348783959618233718423999419319433182886929645941121869949159259673789244719959956749\n" +
        "9991268114199949794791796795151878173829818456228578898645323899918281149142955212695197418994587174\n" +
        "5822959499745689121299788498326889228195559581461939923966779537811589953569287319796289781151896998\n" +
        "9436897921158247555137871611749911768277565988629785829889858951887621699199839538523975486819251893\n" +
        "1571594862927992187996116148364523937892965687869956265897667779393599639466524679734165387399639919\n" +
        "9962786724482327797782587728196679924397986562195996992414831467286182966919287429338691914799216922\n" +
        "2868984576173294283787497486199683551687121917991949629129179396318957289999939916396534179719475154\n" +
        "8161999568288257811833436992819491416421957693693927229887767937937879669299995891838667789831398918\n" +
        "5642987589521545911812978695295887997969939295111585967955999757381277157458472481896569927654279198\n" +
        "1639994457999996998897899825678569915775375379984811345791613225965498722945837199848446799916989575";
}
