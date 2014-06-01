package minidb.je;

import java.io.File;

public class SystemHelpers {
    /**
     Remove a directory and all of its contents.

     The results of executing File.delete() on a File object
     that represents a directory seems to be platform
     dependent. This method removes the directory
     and all of its contents.

     @return true if the complete directory was removed, false if it could not be.
     If false is returned then some of the files in the directory may have been removed.

     */
    public static boolean removeDirectory(File directory) {

        // System.out.println("removeDirectory " + directory);

        if (directory == null)
            return false;
        if (!directory.exists())
            return true;
        if (!directory.isDirectory())
            return false;

        String[] list = directory.list();

        // Some JVMs return null for File.list() when the
        // directory is empty.
        if (list != null) {
            for (int i = 0; i < list.length; i++) {
                File entry = new File(directory, list[i]);

                //        System.out.println("\tremoving entry " + entry);

                if (entry.isDirectory())
                {
                    if (!removeDirectory(entry))
                        return false;
                }
                else
                {
                    if (!entry.delete())
                        return false;
                }
            }
        }

        return directory.delete();
    }
}
