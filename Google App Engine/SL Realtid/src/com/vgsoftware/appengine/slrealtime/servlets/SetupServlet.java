package com.vgsoftware.appengine.slrealtime.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpServletRequest;

import com.vgsoftware.appengine.slrealtime.dataabstraction.Station;
import com.vgsoftware.appengine.slrealtime.dataabstraction.TransportationType;

@SuppressWarnings("serial")
public class SetupServlet extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException
	{
		new TransportationType(1, "Pendeltåg").save();
		new TransportationType(2, "Lokalbana").save();
		new TransportationType(3, "Tunnelbanan").save();
		new TransportationType(4, "Spårvagn").save();

		// Pendeltåg
		new Station("Barkarby", 9703, 59.403576, 17.868834, 1).save();
		new Station("Bro", 9711, 59.51142, 17.635889, 1).save();
		new Station("Bålsta", 9710, 59.569244, 17.531003, 1).save();
		new Station("Farsta strand", 9180, 59.236435, 18.101564, 1).save();
		new Station("Flemingsberg", 9526, 59.217584, 17.946274, 1).save();
		new Station("Gnesta", 9540, 59.048744, 17.311621, 1).save();
		new Station("Gröndalsviken", 9721, 58.899043, 17.932069, 1).save();
		new Station("Handen", 9730, 59.167593, 18.134394, 1).save();
		new Station("Helenelund", 9507, 59.409626, 17.961445, 1).save();
		new Station("Hemfosa", 9725, 59.068868, 17.976422, 1).save();
		new Station("Huddinge", 9527, 59.237302, 17.98022, 1).save();
		new Station("Häggvik", 9505, 59.444399, 17.932262, 1).save();
		new Station("Jakobsberg", 9702, 59.423404, 17.832892, 1).save();
		new Station("Jordbro", 9729, 59.141541, 18.125811, 1).save();
		new Station("Järna", 9542, 59.093322, 17.567267, 1).save();
		new Station("Kallhäll", 9701, 59.453244, 17.805812, 1).save();
		new Station("Karlberg", 9510, 59.339624, 18.029423, 1).save();
		new Station("Krigslida", 9727, 59.109603, 18.067499, 1).save();
		new Station("Kungsängen", 9700, 59.477915, 17.752361, 1).save();
		new Station("Märsta", 9500, 59.628126, 17.861033, 1).save();
		new Station("Mölnbo", 9541, 59.047508, 17.418137, 1).save();
		new Station("Norrviken", 9504, 59.458228, 17.924237, 1).save();
		new Station("Nynäsgård", 9722, 58.913399, 17.942466, 1).save();
		new Station("Nynäshamn", 9720, 58.901121, 17.950962, 1).save();
		new Station("Rosersberg", 9501, 59.58337, 17.879776, 1).save();
		new Station("Rotebro", 9503, 59.476433, 17.914238, 1).save();
		new Station("Rönninge", 9523, 59.193526, 17.749979, 1).save();
		new Station("Segersäng", 9724, 59.029078, 17.926641, 1).save();
		new Station("Skogås", 9731, 59.218211, 18.154306, 1).save();
		new Station("Sollentuna", 9506, 59.428861, 17.948098, 1).save();
		new Station("Solna", 9509, 59.365154, 18.010068, 1).save();
		new Station("Spånga", 9704, 59.383343, 17.898788, 1).save();
		new Station("Stockholms central", 9000, 59.330354, 18.058841, 1).save();
		new Station("Stockholms södra", 9530, 59.314163, 18.064485, 1).save();
		new Station("Stuvsta", 9528, 59.253223, 17.996013, 1).save();
		new Station("Sundbyberg", 9325, 59.360751, 17.970742, 1).save();
		new Station("Södertälje Syd", 9543, 59.162292, 17.645416, 1).save();
		new Station("Södertälje centrum", 9520, 59.192417, 17.626791, 1).save();
		new Station("Södertälje hamn", 9521, 59.17928, 17.646799, 1).save();
		new Station("Trångsund", 9732, 59.228027, 18.129544, 1).save();
		new Station("Tullinge", 9525, 59.205206, 17.903037, 1).save();
		new Station("Tumba", 9524, 59.199735, 17.835617, 1).save();
		new Station("Tungelsta", 9726, 59.102491, 18.044872, 1).save();
		new Station("Ulriksdal", 9508, 59.380742, 18.000283, 1).save();
		new Station("Upplands Väsby", 9502, 59.521826, 17.899647, 1).save();
		new Station("Västerhaninge", 9728, 59.122847, 18.102593, 1).save();
		new Station("Älvsjö", 9529, 59.278774, 18.010969, 1).save();
		new Station("Årstaberg", 9531, 59.300067, 18.029181, 1).save();
		new Station("Ösmo", 9723, 58.98471, 17.902662, 1).save();
		new Station("Östertälje", 9522, 59.184415, 17.659965, 1).save();

		// Roslagsbanan
		new Station("Altorp", 9683, 59.410194, 18.072874, 2).save();
		new Station("Bråvallavägen", 9636, 59.405585, 18.060579, 2).save();
		new Station("Bällsta", 9627, 59.523942, 18.071844, 2).save();
		new Station("Djursholms Ekeby", 9635, 59.412727, 18.057403, 2).save();
		new Station("Djursholms Ösby", 9637, 59.397978, 18.058648, 2).save();
		new Station("Ekskogen", 9621, 59.639044, 18.226983, 2).save();
		new Station("Enebyberg", 9634, 59.425565, 18.051288, 2).save();
		new Station("Ensta", 9631, 59.453468, 18.063658, 2).save();
		new Station("Frösunda", 9622, 59.624204, 18.170464, 2).save();
		new Station("Galoppfältet", 9668, 59.447147, 18.085148, 2).save();
		new Station("Hägernäs", 9666, 59.451155, 18.124641, 2).save();
		new Station("Kragstalund", 9628, 59.509264, 18.075857, 2).save();
		new Station("Kårsta", 9620, 59.656777, 18.267441, 2).save();
		new Station("Lahäll", 9682, 59.427399, 18.085341, 2).save();
		new Station("Lindholmen", 9623, 59.584152, 18.109266, 2).save();
		new Station("Molnby", 9624, 59.556526, 18.08474, 2).save();
		new Station("Mörby", 9638, 59.391871, 18.046921, 2).save();
		new Station("Näsby allé", 9681, 59.427399, 18.085341, 2).save();
		new Station("Näsbypark", 9680, 59.43052, 18.096156, 2).save();
		new Station("Ormsta", 9625, 59.545995, 18.079504, 2).save();
		new Station("Roslags Näsby", 9633, 59.435245, 18.057296, 2).save();
		new Station("Rydbo", 9665, 59.465325, 18.183231, 2).save();
		new Station("Stockholms östra", 9600, 59.34609, 18.071705, 2).save();
		new Station("Stocksund", 9639, 59.385086, 18.043928, 2).save();
		new Station("Tibble", 9632, 59.442467, 18.062596, 2).save();
		new Station("Tunagård", 9661, 59.469004, 18.307471, 2).save();
		new Station("Täby centrum", 9669, 59.44394, 18.074054, 2).save();
		new Station("Täby kyrkby", 9629, 59.460997, 18.062725, 2).save();
		new Station("Täljö", 9664, 59.472389, 18.233485, 2).save();
		new Station("Universitetet", 9203, 59.360309, 18.056674, 2).save();
		new Station("Vallentuna", 9626, 59.533327, 18.079805, 2).save();
		new Station("Vendevägen", 9685, 59.399906, 18.067971, 2).save();
		new Station("Viggbyholm", 9667, 59.449045, 18.103859, 2).save();
		new Station("Visinge", 9630, 59.460997, 18.062725, 2).save();
		new Station("Åkers Runö", 9663, 59.480786, 18.268901, 2).save();
		new Station("Åkersberga", 9662, 59.479151, 18.298437, 2).save();
		new Station("Östberga", 9684, 59.402839, 18.073024, 2).save();
		new Station("Österskär", 9660, 59.460981, 18.311945, 2).save();

		// Tunnelbana
		new Station("Abrahamsberg", 9110, 59.336675, 17.952947, 3).save();
		new Station("Akalla", 9300, 59.414198, 17.920315, 3).save();
		new Station("Alby", 9282, 59.239508, 17.845573, 3).save();
		new Station("Alvik", 9112, 59.333616, 17.980263, 3).save();
		new Station("Aspudden", 9293, 59.306453, 18.001528, 3).save();
		new Station("Axelsberg", 9291, 59.30435, 17.975392, 3).save();
		new Station("Bagarmossen", 9141, 59.276274, 18.131453, 3).save();
		new Station("Bandhagen", 9163, 59.270354, 18.049335, 3).save();
		new Station("Bergshamra", 9202, 59.381551, 18.03659, 3).save();
		new Station("Björkhagen", 9143, 59.29112, 18.11551, 3).save();
		new Station("Blackeberg", 9105, 59.348333, 17.882813, 3).save();
		new Station("Blåsut", 9187, 59.29026, 18.090963, 3).save();
		new Station("Bredäng", 9289, 59.294906, 17.933764, 3).save();
		new Station("Brommaplan", 9109, 59.338376, 17.939257, 3).save();
		new Station("Danderyds sjukhus", 9201, 59.39191, 18.04131, 3).save();
		new Station("Duvbo", 9324, 59.368472, 17.96621, 3).save();
		new Station("Enskede gård", 9167, 59.2894, 18.070289, 3).save();
		new Station("Farsta strand", 9180, 59.234991, 18.101724, 3).save();
		new Station("Farsta", 9181, 59.24337, 18.093194, 3).save();
		new Station("Fittja", 9283, 59.247453, 17.86098, 3).save();
		new Station("Fridhemsplan", 9115, 59.333435, 18.03477, 3).save();
		new Station("Fruängen", 9260, 59.285811, 17.964964, 3).save();
		new Station("Gamla stan", 9193, 59.323294, 18.067059, 3).save();
		new Station("Globen", 9168, 59.294248, 18.077917, 3).save();
		new Station("Gubbängen", 9183, 59.262853, 18.082058, 3).save();
		new Station("Gullmarsplan", 9189, 59.299025, 18.080835, 3).save();
		new Station("Gärdet", 9221, 59.34667, 18.099589, 3).save();
		new Station("Hagsätra", 9160, 59.262722, 18.012514, 3).save();
		new Station("Hallonbergen", 9303, 59.374975, 17.972149, 3).save();
		new Station("Hallunda", 9281, 59.243239, 17.825618, 3).save();
		new Station("Hammarbyhöjden", 9144, 59.294736, 18.104546, 3).save();
		new Station("Hjulsta", 9320, 59.397266, 17.892071, 3).save();
		new Station("Hornstull", 9295, 59.315871, 18.033886, 3).save();
		new Station("Husby", 9301, 59.410551, 17.929213, 3).save();
		new Station("Huvudsta", 9327, 59.350044, 17.989396, 3).save();
		new Station("Hägerstensåsen", 9262, 59.295607, 17.979125, 3).save();
		new Station("Hässelby gård", 9101, 59.366875, 17.84377, 3).save();
		new Station("Hässelby strand", 9100, 59.361266, 17.832344, 3).save();
		new Station("Högdalen", 9162, 59.263818, 18.043026, 3).save();
		new Station("Hökarängen", 9182, 59.257742, 18.082809, 3).save();
		new Station("Hötorget", 9119, 59.335553, 18.063583, 3).save();
		new Station("Islandstorget", 9106, 59.345833, 17.894024, 3).save();
		new Station("Johannelund", 9102, 59.367925, 17.85745, 3).save();
		new Station("Karlaplan", 9222, 59.338836, 18.090835, 3).save();
		new Station("Kista", 9302, 59.403337, 17.946463, 3).save();
		new Station("Kristineberg", 9113, 59.332691, 18.00319, 3).save();
		new Station("Kungsträdgården", 9340, 59.331288, 18.076427, 3).save();
		new Station("Kärrtorp", 9142, 59.284473, 18.114438, 3).save();
		new Station("Liljeholmen", 9294, 59.310746, 18.022814, 3).save();
		new Station("Mariatorget", 9297, 59.316977, 18.063326, 3).save();
		new Station("Masmo", 9284, 59.249677, 17.880329, 3).save();
		new Station("Medborgarplatsen", 9191, 59.31436, 18.073454, 3).save();
		new Station("Midsommarkransen", 9264, 59.301852, 18.012042, 3).save();
		new Station("Mälarhöjden", 9290, 59.300932, 17.957282, 3).save();
		new Station("Mörby centrum", 9200, 59.398727, 18.036203, 3).save();
		new Station("Norsborg", 9280, 59.243832, 17.814417, 3).save();
		new Station("Näckrosen", 9304, 59.367122, 17.986903, 3).save();
		new Station("Odenplan", 9117, 59.342994, 18.049636, 3).save();
		new Station("Rinkeby", 9322, 59.388624, 17.932315, 3).save();
		new Station("Rissne", 9323, 59.376333, 17.943161, 3).save();
		new Station("Ropsten", 9223, 59.357324, 18.102121, 3).save();
		new Station("Råcksta", 9104, 59.354797, 17.881826, 3).save();
		new Station("Rådhuset", 9309, 59.330651, 18.045749, 3).save();
		new Station("Rådmansgatan", 9118, 59.340565, 18.058691, 3).save();
		new Station("Rågsved", 9161, 59.256581, 18.028134, 3).save();
		new Station("S:t Eriksplan", 9116, 59.339909, 18.036632, 3).save();
		new Station("Sandsborg", 9186, 59.284758, 18.092401, 3).save();
		new Station("Skanstull", 9190, 59.307899, 18.076158, 3).save();
		new Station("Skarpnäck", 9140, 59.266757, 18.133342, 3).save();
		new Station("Skogskyrkogården", 9185, 59.27919, 18.09549, 3).save();
		new Station("Skärholmen", 9287, 59.277174, 17.9069, 3).save();
		new Station("Skärmarbrink", 9188, 59.295476, 18.090362, 3).save();
		new Station("Slussen", 9192, 59.319528, 18.072295, 3).save();
		new Station("Sockenplan", 9166, 59.283296, 18.070589, 3).save();
		new Station("Solna centrum", 9305, 59.358779, 17.998717, 3).save();
		new Station("Stadion", 9205, 59.342994, 18.081779, 3).save();
		new Station("Stadshagen", 9307, 59.337223, 18.021148, 3).save();
		new Station("Stora mossen", 9111, 59.334524, 17.966176, 3).save();
		new Station("Stureby", 9164, 59.274608, 18.055601, 3).save();
		new Station("Sundbybergs centrum", 9325, 59.361691, 17.975454, 3).save();
		new Station("Svedmyra", 9165, 59.283334, 18.070536, 3).save();
		new Station("Sätra", 9288, 59.285022, 17.921362, 3).save();
		new Station("T-Centralen", 9001, 59.330792, 18.063088, 3).save();
		new Station("Tallkrogen", 9184, 59.2711, 18.085298, 3).save();
		new Station("Tekniska högskolan", 9204, 59.34585, 18.071694, 3).save();
		new Station("Telefonplan", 9263, 59.298324, 17.997193, 3).save();
		new Station("Tensta", 9321, 59.394981, 17.90468, 3).save();
		new Station("Thorildsplan", 9114, 59.331788, 18.015432, 3).save();
		new Station("Universitetet", 9203, 59.365526, 18.054872, 3).save();
		new Station("Vreten", 9326, 59.354621, 17.976657, 3).save();
		new Station("Vällingby", 9103, 59.36324, 17.872052, 3).save();
		new Station("Västertorp", 9261, 59.291444, 17.966638, 3).save();
		new Station("Västra skogen", 9306, 59.347974, 18.00734, 3).save();
		new Station("Vårberg", 9286, 59.275968, 17.89012, 3).save();
		new Station("Vårby gård", 9285, 59.264631, 17.884369, 3).save();
		new Station("Zinkensdamm", 9296, 59.31782, 18.050065, 3).save();
		new Station("Ängbyplan", 9107, 59.341856, 17.906942, 3).save();
		new Station("Åkeshov", 9108, 59.342037, 17.924899, 3).save();
		new Station("Örnsberg", 9292, 59.305555, 17.989168, 3).save();
		new Station("Östermalmstorg", 9206, 59.334962, 18.074012, 3).save();

		// Tvärbanan
		new Station("Alvik", 9112, 59.33322773187615, 17.97965168952942, 4).save();
		new Station("Alviks strand", 9812, 59.32888801573286, 17.98211932182312, 4).save();
		new Station("Stora Essingen", 9811, 59.324766687715886, 17.993041276931763, 4).save();
		new Station("Gröndal", 1605, 59.31580668526209, 18.010443449020386, 4).save();
		new Station("Trekanten", 1603, 59.31407517684315, 18.018372058868408, 4).save();
		new Station("Liljeholmen", 9294, 59.310822713968925, 18.02454113960266, 4).save();
		new Station("Årstadal", 9807, 59.30582295146178, 18.02552819252014, 4).save();
		new Station("Årstaberg", 9531, 59.2994147332507, 18.02930474281311, 4).save();
		new Station("Årstafältet", 1521, 59.29636903714818, 18.039711713790894, 4).save();
		new Station("Valla torg", 1525, 59.29504878560735, 18.048434257507324, 4).save();
		new Station("Linde", 1503, 59.29334498280576, 18.064184188842773, 4).save();
		new Station("Globen", 9168, 59.29415032788101, 18.07647943496704, 4).save();
		new Station("Gullmarsplan", 9189, 59.29944212136717, 18.08027744293213, 4).save();
		new Station("Mårtensdal", 1555, 59.30272579672863, 18.088141679763794, 4).save();
		new Station("Luma", 1552, 59.304174456814664, 18.095828890800476, 4).save();
		new Station("Sickla kaj", 1550, 59.30285450815607, 18.103601932525635, 4).save();
		new Station("Sickla udde", 9820, 59.30620904912501, 18.108880519866943, 4).save();

		// Spårväg City
		new Station("Sergels torg", 1000, 59.33262, 18.06618, 4).save();
		new Station("Kungsträdgården", 1016, 59.33276, 18.07069, 4).save();
		new Station("Norrmalmstorg", 1015, 59.33342, 18.07345, 4).save();
		new Station("Nybroplan", 1105, 59.33272, 18.07706, 4).save();
		new Station("Styrmansgatan", 1107, 59.33155, 18.08504, 4).save();
		new Station("Djurgårdsbron", 1100, 59.33181, 18.09236, 4).save();
		new Station("Nordiska museet/Vasamuseet", 1408, 59.32930, 18.09562, 4).save();
		new Station("Liljevalchs/Gröna Lund", 1406, 59.32524, 18.09729, 4).save();
		new Station("Skansen", 1405, 59.32396, 18.10064, 4).save();
		new Station("Djurgårdsskolan", 1403, 59.32298, 18.10493, 4).save();
		new Station("Bellmansro", 1409, 59.32398, 18.11053, 4).save();
		new Station("Waldemarsudde", 1400, 59.32263, 18.11133, 4).save();

		// Saltsjöbanan
		new Station("Slussen", 9192, 59.31952810332031, 18.071951866149902, 2).save();
		new Station("Henriksdal", 9432, 59.31230114475141, 18.108086585998535, 2).save();
		new Station("Sickla", 9431, 59.306847059062214, 18.121304512023926, 2).save();
		new Station("Nacka", 9430, 59.30654037766777, 18.12997341156006, 2).save();
		new Station("Saltsjö-Järla", 9429, 59.30686896477028, 18.149585723876953, 2).save();
		new Station("Lillängen", 9428, 59.305182183964995, 18.161344528198242, 2).save();
		new Station("Storängen", 9427, 59.30559841037957, 18.178038597106934, 2).save();
		new Station("Saltsjö-Duvnäs", 9426, 59.300559537718414, 18.19859504699707, 2).save();
		new Station("Östervik", 9425, 59.29510356884767, 18.23558807373047, 2).save();
		new Station("Fisksätra", 9424, 59.29418319870373, 18.25657367706299, 2).save();
		new Station("Igelboda", 9423, 59.289800142200335, 18.275842666625977, 2).save();
		new Station("Tippen", 9443, 59.2839786, 18.2772057, 2).save();
		new Station("Tattby", 9442, 59.27910311493099, 18.281936645507812, 2).save();
		new Station("Erstaviksbadet", 9441, 59.2729858860848, 18.285112380981445, 2).save();
		new Station("Solsidan", 9440, 59.27116585322382, 18.296356201171875, 2).save();
		new Station("Neglinge", 9422, 59.288572785213915, 18.292150497436523, 2).save();
		new Station("Ringvägen", 9421, 59.28304913102033, 18.30219268798828, 2).save();
		new Station("Saltsjöbaden", 9420, 59.27903734412124, 18.313007354736328, 2).save();

		// Lidingöbanan
		new Station("Ropsten", 9220, 59.35732413640648, 18.10248613357544, 2).save();
		new Station("Torsvik", 9252, 59.3619171965557, 18.117785453796387, 2).save();
		new Station("Baggeby", 9251, 59.35689760642419, 18.133835792541504, 2).save();
		new Station("Bodal", 9250, 59.353676585168934, 18.138502836227417, 2).save();
		new Station("Larsberg", 9249, 59.35048807467962, 18.14620614051819, 2).save();
		new Station("AGA", 9248, 59.34663739870551, 18.155089616775513, 2).save();
		new Station("Skärsätra", 9219, 59.34336616898086, 18.170442581176758, 2).save();
		new Station("Kottla", 9245, 59.34442743857396, 18.179798126220703, 2).save();
		new Station("Högberga", 9244, 59.34395151249717, 18.19308042526245, 2).save();
		new Station("Brevik", 9243, 59.34840963904922, 18.203551769256592, 2).save();
		new Station("Käppala", 9242, 59.35273592367938, 18.218003511428833, 2).save();
		new Station("Talludden", 9241, 59.355415646490954, 18.22254180908203, 2).save();
		new Station("Gåshaga", 9240, 59.356935884974874, 18.22910785675049, 2).save();
		new Station("Gåshaga Brygga", 9239, 59.35687573294728, 18.234118223190308, 2).save();

		// Nockebybanan
		new Station("Nockeby", 9120, 59.32864720819035, 17.91866898536682, 2).save();
		new Station("Nockeby torg", 9121, 59.32899200028048, 17.928314208984375, 2).save();
		new Station("Olovslund", 9122, 59.32791930228881, 17.93522357940674, 2).save();
		new Station("Höglandstorget", 9123, 59.323359958036626, 17.93995499610901, 2).save();
		new Station("Ålstens gård", 9124, 59.32061201461015, 17.952078580856323, 2).save();
		new Station("Ålstensgatan", 9125, 59.32332711540307, 17.956466674804688, 2).save();
		new Station("Smedslätten", 9126, 59.32091856910908, 17.964341640472412, 2).save();
		new Station("Klövervägen", 9127, 59.324717425792606, 17.973836660385132, 2).save();
		new Station("Alléparken", 9128, 59.32904672886196, 17.974212169647217, 2).save();
	}
}
