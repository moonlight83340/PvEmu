//package org.pvemu.jelly;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.util.HashMap;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.pvemu.jelly.Shell.GraphicRenditionEnum;
//
//public class OldConfig {
//
//    private static OldConfig self = null;
//    private final HashMap<String, String> configData = new HashMap<>();
//
//    public static String getString(String param, String defaultValue) {
//        if (!self.configData.containsKey(param.toLowerCase())) {
//            return defaultValue;
//        }
//        return self.configData.get(param.toLowerCase());
//    }
//
//    public static String getString(String param) {
//        return getString(param, "");
//    }
//
//    public static int getInt(String param, int defaultValue) {
//        if (self.configData.containsKey(param.toLowerCase())) {
//            try {
//                return Integer.parseInt(self.configData.get(param.toLowerCase()));
//            } catch (Exception e) {
//                return defaultValue;
//            }
//        }
//        return defaultValue;
//    }
//
//    public static int getInt(String param) {
//        return getInt(param, 0);
//    }
//
//    public static boolean getBool(String param) {
//        if (!self.configData.containsKey(param.toLowerCase())) {
//            return false;
//        }
//
//        String value = self.configData.get(param.toLowerCase());
//
//        switch (value) {
//            case "0":
//            case "off":
//            case "no":
//            case "false":
//            case "non":
//                return false;
//            case "1":
//            case "on":
//            case "yes":
//            case "true":
//            case "oui":
//                return true;
//            default:
//                return false;
//        }
//    }
//
//    private OldConfig() {
//        Shell.print("Chargement de la configuration : ", GraphicRenditionEnum.YELLOW);
//        File f = new File(Constants.CONFIG_FILE);
//
//        if (!f.exists()) {
//            create(f);
//        }
//
//        try {
//            FileReader FW = new FileReader(f);
//            BufferedReader file = new BufferedReader(FW);
//            String line;
//            String param;
//            String value;
//
//            while ((line = file.readLine()) != null) {
//                try {
//                    param = line.split("=")[0].toLowerCase().trim();
//                    value = line.split("=")[1].toLowerCase().trim();
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    continue;
//                }
//
//                configData.put(param, value);
//            }
//
//            file.close();
//            FW.close();
//            Shell.println("Ok", GraphicRenditionEnum.GREEN);
//        } catch (Exception ex) {
//            System.out.println("Erreur lors du chargement");
//            System.exit(1);
//        }
//    }
//
//    public static void load() {
//        if (self == null) {
//            self = new OldConfig();
//        }
//    }
//
//    public static void set(String param, String value) {
//        param = param.trim().toUpperCase();
//
//        File f = new File(Constants.CONFIG_FILE);
//        File tmp = new File("~" + Constants.CONFIG_FILE);
//
//        if (!f.exists()) {
//            create(f);
//        }
//
//        try {
//            FileWriter FW = new FileWriter(tmp);
//            BufferedReader BR = new BufferedReader(new FileReader(f));
//
//            String line;
//            String[] splited;
//            boolean found = false;
//            while ((line = BR.readLine()) != null) {
//                splited = line.split("=");
//                if (splited.length == 2 && splited[0].trim().toUpperCase().equals(param)) {
//                    FW.append(param).append("=").append(value.trim());
//                    found = true;
//                } else {
//                    FW.append(line).append(System.lineSeparator());
//                }
//            }
//            BR.close();
//
//            if (!found) {
//                FW.append(param).append("=").append(value.trim());
//            }
//            FW.flush();
//            f.renameTo(new File(Constants.CONFIG_FILE + ".old"));
//            tmp.renameTo(f);
//
//        } catch (Exception ex) {
//            Logger.getLogger(OldConfig.class.getName()).log(Level.SEVERE, "Chargement impossible du fichier de configuration", ex);
//        }
//    }
//
//    private static void create(File file) {
//        try {
//            file.createNewFile();
//            file.setReadable(true, false);
//            file.setWritable(true, false);
//            if (!file.canWrite()) {
//                System.exit(1);
//            }
//
//            FileWriter FW = new FileWriter(file);
//            FW.write(
//                    "#####################################\n"
//                    + "# Fichier de configuration de Jelly #\n"
//                    + "# By v4vx                           #\n"
//                    + "#                       Version 0.1 #\n"
//                    + "#####################################\n"
//                    + "\n"
//                    + "\n"
//                    + "\n"
//                    + "\n"
//                    + "#############################\n"
//                    + "###Configuration de la bdd###\n"
//                    + "#############################\n"
//                    + "\n"
//                    + "#IP de la base de données\n"
//                    + "DB_HOST=127.0.0.1\n"
//                    + "\n"
//                    + "#Utilisateur\n"
//                    + "DB_USER=root\n"
//                    + "\n"
//                    + "#mot de passe\n"
//                    + "DB_PASS=\n"
//                    + "\n"
//                    + "#Nom de la bdd\n"
//                    + "DB_NAME=jelly\n"
//                    + "\n"
//                    + "\n"
//                    + "##############################\n"
//                    + "###Configuration du serveur###\n"
//                    + "##############################\n"
//                    + "\n"
//                    + "#Port du serveur de connexion (à bien noter dans le config.xml de dofus)\n"
//                    + "REALM_PORT=444\n"
//                    + "#Ip de jeu, la même que dans config.xml\n"
//                    + "GAME_IP=127.0.0.1\n"
//                    + "#Port de jeu (quelconque, mais inutilisé !)\n"
//                    + "GAME_PORT=5555\n"
//                    + "\n\n"
//                    + "########################\n"
//                    + "###Performances & co.###\n"
//                    + "########################\n"
//                    + "\n"
//                    + "PRELOAD_MAPS=false\n"
//                    + "PRELOAD_ACCOUNTS=false\n"
//                    + "FREE_UNUSED_ACCOUNTS=true");
//            FW.flush();
//            FW.close();
//
//        } catch (Exception e) {
//            System.exit(1);
//        }
//    }
//}
