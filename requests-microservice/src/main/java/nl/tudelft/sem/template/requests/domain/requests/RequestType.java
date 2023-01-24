package nl.tudelft.sem.template.requests.domain.requests;

public enum RequestType {
    VIEW, EDIT
}


//public enum RequestType {
//    VIEW("V"), EDIT("E");
//
//    private static String shortName;
//
//    public RequestType(String shortName){
//        this.shortName = shortName;
//    }
//
//    public static RequestType fromShortName(String shortname){
//        switch (shortname){
//            case "V":
//                return RequestType.VIEW;
//            case "E":
//                return RequestType.EDIT;
//            default:
//                throw new IllegalArgumentException("ShortName [" + shortName + "] not supported.");
//        }
//    }
//
//    public String getShortName() {
//        return shortName;
//    }
//}
