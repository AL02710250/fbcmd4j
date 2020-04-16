/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fbcmd4j;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import facebook4j.Facebook;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import java.io.IOException;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;






/**
 *
 * @author kleskl22
 */
public class Fbcmd4j 
{
        private static final String CONFIG_DIR = "config";
	private static final String CONFIG_FILE = "fbcmd4j.properties";
        static final Logger logger = LogManager.getLogger(Fbcmd4j.class);
    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) 
    {
        logger.info("Iniciando");
        Properties props = null;
	String dato;
	try 
        {
            props = utils.loadConfigFile(CONFIG_DIR, CONFIG_FILE);
	} 
        catch (IOException ex) 
        {
            logger.error(ex);
	}
	int option = 1;
	try 
        {
            Scanner scan = new Scanner(System.in);
            while(true) 
            {
                Facebook fb = utils.configFacebook(props);
		String userId = fb.getId();
		System.out.println("Selecciona ela opcion del siguiente Menu:\n\n"
                                +  "(1) Configurar Cliente \n"
                                +  "(2) Obtener NewsFeed \n"
                                +  "(3) Obtener Wall \n"
                                +  "(4) Publicar Estado \n"
                                +  "(5) Publicar Link \n"
                                +  "(6) Salir \n");
                try 
                {
                    option = scan.nextInt();
                    scan.nextLine();
                    switch (option) 
                    {
                        case 1:
                            utils.configTokens(CONFIG_DIR, CONFIG_FILE, props, scan);
                            props = utils.loadConfigFile(CONFIG_DIR, CONFIG_FILE);
                            break;
			case 2:
                            System.out.println("Obtener NewsFeed");
                            ResponseList<Post> feeds = fb.getFeed(userId,
                            new Reading().limit(50));
                            saveFile("Feeds",feeds,scan);
                            break;
			case 3:
                            System.out.println("Obtener Wall");
                            ResponseList<Post> wall = fb.getPosts();
                            saveFile("Wall",wall,scan);
                            break;
                        case 4:
                            System.out.println("Obtener estado: ");
                            dato = scan.nextLine();
                            fb.postStatusMessage(dato);
                            break;
                        case 5:
                            System.out.println("Obtener link: ");
                            dato = scan.nextLine();
                            String descripcion;						
                            System.out.println("Ingresa la descripcion del link: ");
                            descripcion = scan.nextLine();
                            fb.postLink(new URL(dato), descripcion);
                            break;
			case 6:
                            System.exit(0);
                            break;
                            default:
                            break;
                    }
		} 
                catch (InputMismatchException ex) 
                {
                    System.out.println("Fail - favor de revisar log.");
                    logger.error("La opcion no es correcta. %s. \n", ex.getClass());
                } 
                catch (Exception ex) 
                {
                    System.out.println("Fail - favor de revisar log.");
                    logger.error(ex);
		}
                    System.out.println();
            }
	} 
        catch (Exception e) 
        {
            logger.error(e);
	} 
    }
}

