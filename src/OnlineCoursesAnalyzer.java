import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This is just a demo for you, please run it on JDK17. This is just a demo, and you can extend and
 * implement functions based on this demo, or implement it in a different way.
 */
public class OnlineCoursesAnalyzer {

    List<Course> courses = new ArrayList<>();

    public OnlineCoursesAnalyzer(String datasetPath) {
        BufferedReader br = null;
        String line;
        try {
            br = new BufferedReader(new FileReader(datasetPath, StandardCharsets.UTF_8));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] info = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
                Course course = new Course(info[0], info[1], new Date(info[2]), info[3], info[4],
                    info[5],
                    Integer.parseInt(info[6]), Integer.parseInt(info[7]), Integer.parseInt(info[8]),
                    Integer.parseInt(info[9]), Integer.parseInt(info[10]),
                    Double.parseDouble(info[11]),
                    Double.parseDouble(info[12]), Double.parseDouble(info[13]),
                    Double.parseDouble(info[14]),
                           Double.parseDouble(info[15]), Double.parseDouble(info[16]),
                    Double.parseDouble(info[17]),
                    Double.parseDouble(info[18]), Double.parseDouble(info[19]),
                     Double.parseDouble(info[20]),

                      Double.parseDouble(info[21]), Double.parseDouble(info[22]));

                courses.add(course);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                      if (br != null) {
                     try {
                              br.close();
                      } catch (IOException e) {
                                  e.printStackTrace();
                 }
            }
        }
    }

    //1
        public Map<String, Integer> getPtcpCountByInst() {

         Map<String, Integer> m = courses.stream().collect(

            Collectors.groupingBy(c -> c.institution, Collectors.summingInt(c -> c.participants)));

            return m;
    }

    //2
    public Map<String, Integer> getPtcpCountByInstAndSubject() {

           Map<String, Integer> m = courses.stream().collect(
               Collectors.groupingBy(c -> c.institution + "-" + c.subject,
                 Collectors.summingInt(c -> c.participants)));

          Map<String, Integer> mm = m.entrySet().stream().sorted(
              (c1, c2) -> c2.getValue() != c1.getValue() ? c2.getValue() - c1.getValue()

                    : c1.getKey().compareTo(c2.getKey())).collect(

              Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (c1, c2) -> c1,

                 LinkedHashMap::new));

          return mm;
    }

    //3
        public Map<String, List<List<String>>> getCourseListOfInstructor() {

          Map<String, List<List<String>>> m = new HashMap<>();
         for (int i = 0; i < courses.size(); i++) {
             String[] t = courses.get(i).instructors.split(", ");
             for (int j = 0; j < t.length; j++) {
                String s = t[j];

                if (m.get(s) == null) {
                        List<List<String>> l = new ArrayList<>();
                    List<String> a = new ArrayList<>();

                     List<String> b = new ArrayList<>();

                    for (int k = 0; k < courses.size(); k++) {
                            String[] tt = courses.get(k).instructors.split(", ");

                        boolean h = false;

                        for (int p = 0; p < tt.length; p++) {

                                if (s.equals(tt[p])) {
                                h = true;
                                break;
                            }
                        }
                        if (h) {

                            int mm = courses.get(k).instructors.split(",").length;

                            if (mm == 1) {

                                a.add(courses.get(k).title);


                            } else {

                                    b.add(courses.get(k).title);

                            }
                        }
                    }
                               a = a.stream().distinct().sorted().toList();

                       b = b.stream().distinct().sorted().toList();

                    l.add(a);
                    l.add(b);
                     m.put(s, l);
                }

            }
        }

        return m;

    }

    //4
        public List<String> getCourses(int topK, String by) {
        if (by.equals("hours")) {
            List<Course> m = courses.stream()
                .sorted((c1, c2) -> c1.totalHours != c2.totalHours ? (int) (
                    c2.totalHours - c1.totalHours) : c1.title.compareTo(c2.title)).toList();

            List<String> l = m.stream().map(c -> c.title).distinct().limit(topK).toList();

            return l;
        } else {
            List<Course> m = courses.stream()
                .sorted((c1, c2) -> c1.participants != c2.participants ? (int) (

                    c2.participants - c1.participants) : c1.title.compareTo(c2.title)).toList();

            List<String> l = m.stream().map(c -> c.title).distinct().limit(topK).toList();
            return l;


        }


    }

    //5


    public List<String> searchCourses(String courseSubject, double percentAudited,
        double totalCourseHours) {

        String l = courseSubject.toUpperCase();
        List<String> a = new ArrayList<>();

        for (int i = 0; i < courses.size(); i++) {

            if (courses.get(i).subject.toUpperCase().contains(l)
                && courses.get(i).percentAudited >= percentAudited
                && courses.get(i).totalHours <= totalCourseHours) {

                a.add(courses.get(i).title);
            }
        }
        return a.stream().sorted().distinct().toList();
    }

    //6
    public List<String> recommendCourses(int age, int gender, int isBachelorOrHigher) {
        return null;

    }


}

class Course {

    String institution;
    String number;
    Date launchDate;
    String title;
    String instructors;
    String subject;
    int year;
    int honorCode;
    int participants;
    int audited;
    int certified;
    double percentAudited;
    double percentCertified;
    double percentCertified50;
    double percentVideo;
    double percentForum;
    double gradeHigherZero;
    double totalHours;
    double medianHoursCertification;
    double medianAge;


    double percentMale;
    double percentFemale;
    double percentDegree;


    public Course(String institution, String number, Date launchDate,
        String title, String instructors, String subject,
        int year, int honorCode, int participants,
        int audited, int certified, double percentAudited,
                 double percentCertified, double percentCertified50,
             double percentVideo, double percentForum, double gradeHigherZero,
                double totalHours, double medianHoursCertification,
        double medianAge, double percentMale, double percentFemale,
        double percentDegree) {
        this.institution = institution;
         this.number = number;
         this.launchDate = launchDate;
          if (title.startsWith("\"")) {
            title = title.substring(1);
        }
         if (title.endsWith("\"")) {
             title = title.substring(0, title.length() - 1);
        }
        this.title = title;
        if (instructors.startsWith("\"")) {
            instructors = instructors.substring(1);
        }
         if (instructors.endsWith("\"")) {
            instructors = instructors.substring(0, instructors.length() - 1);
        }
        this.instructors = instructors;
         if (subject.startsWith("\"")) {
            subject = subject.substring(1);
        }
        if (subject.endsWith("\"")) {
            subject = subject.substring(0, subject.length() - 1);
          }
        this.subject = subject;
         this.year = year;
        this.honorCode = honorCode;
        this.participants = participants;
         this.audited = audited;
        this.certified = certified;
        this.percentAudited = percentAudited;
        this.percentCertified = percentCertified;
        this.percentCertified50 = percentCertified50;
        this.percentVideo = percentVideo;
          this.percentForum = percentForum;
        this.gradeHigherZero = gradeHigherZero;
        this.totalHours = totalHours;
                this.medianHoursCertification = medianHoursCertification;
        this.medianAge = medianAge;
               this.percentMale = percentMale;
        this.percentFemale = percentFemale;
                  this.percentDegree = percentDegree;
    }

}