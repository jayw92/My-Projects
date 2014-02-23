
/*
 * StringPlayApp.java
 */

package stringplay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.Task;


public class StringPlayApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new Start(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of StringPlayApp
     */
    public static StringPlayApp getApplication() {
        return Application.getInstance(StringPlayApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(StringPlayApp.class, args);
    }

    /**
     * A Task that saves a text String to a file.  The file is not appended
     * to, its contents are replaced by the String.
     */
    static class SaveFileTask extends Task<Void, Void> {

        private final File file;
        private final String text;

        /**
         * Construct a SaveTextFileTask.
         *
         * @param file The file to save to
         * @param text The new contents of the file
         */
        SaveFileTask(Application app, File file, String text) {
            super(app);
            File incase;
            String n=file.getPath();
            if (n.endsWith(".spf")){
                incase=new File(n);
            }
            else{
                n=n+".spf";
                incase=new File(n);
            }
            this.file = incase;
            this.text = text;
        }

        /**
         * Return the File that the {@link #getText text} will be
         * written to.
         *
         * @return the value of the read-only file property.
         */
        public final File getFile() {
            return file;
        }

        /**
         * Return the String that will be written to the
         * {@link #getFile file}.
         *
         * @return the value of the read-only text property.
         */
        public final String getText() {
            return text;
        }

        private void renameFile(File oldFile, File newFile) throws IOException {
            if (!oldFile.renameTo(newFile)) {
                String fmt = "file rename failed: %s => %s";
                throw new IOException(String.format(fmt, oldFile, newFile));
            }
        }

        
        @Override
        protected Void doInBackground() throws IOException {
            String absPath = file.getAbsolutePath();
            File tmpFile = new File(absPath + ".tmp");
            tmpFile.createNewFile();
            tmpFile.deleteOnExit();
            File backupFile = new File(absPath + ".bak");
            BufferedWriter out = null;
            int fileLength = text.length();
            int blockSize = Math.max(1024, 1 + ((fileLength - 1) / 100));
            try {
                out = new BufferedWriter(new FileWriter(tmpFile));
                int offset = 0;
                while (!isCancelled() && (offset < fileLength)) {
                    int length = Math.min(blockSize, fileLength - offset);
                    out.write(text, offset, length);
                    offset += blockSize;
                    setProgress(Math.min(offset, fileLength), 0, fileLength);
                }
            } finally {
                if (out != null) {
                    out.close();
                }
            }
            if (!isCancelled()) {
                backupFile.delete();
                if (file.exists()) {
                    renameFile(file, backupFile);
                }
                renameFile(tmpFile, file);
            } else {
                tmpFile.delete();
            }
            backupFile.deleteOnExit();
            return null;
        }
    }

    /**
     * A Task that loads the contents of a file into a String.
     */
    static class LoadFileTask extends Task<String, Void> {

        private final File file;

        /**
         * Construct a LoadTextFileTask.
         *
         * @param file the file to load from.
         */
        LoadFileTask(Application application, File file) {
            super(application);
            this.file = file;
        }

        /**
         * Return the file being loaded.
         *
         * @return the value of the read-only file property.
         */
        public final File getFile() {
            return file;
        }

        /**
         * Load the file into a String and return it.  The
         * {@code progress} property is updated as the file is loaded.
         * <p>
         * If this task is cancelled before the entire file has been
         * read, null is returned.
         *
         * @return the contents of the {code file} as a String or null
         */
        @Override
        protected String doInBackground() throws IOException {
            int fileLength = (int) file.length();
            int nChars = -1;
            // progress updates after every blockSize chars read
            int blockSize = Math.max(1024, fileLength / 100);
            int p = blockSize;
            char[] buffer = new char[32];
            StringBuilder contents = new StringBuilder();
            BufferedReader rdr = new BufferedReader(new FileReader(file));
            while (!isCancelled() && (nChars = rdr.read(buffer)) != -1) {
                contents.append(buffer, 0, nChars);
                if (contents.length() > p) {
                    p += blockSize;
                    setProgress(contents.length(), 0, fileLength);
                }
            }
            if (!isCancelled()) {
                return contents.toString();
            } else {
                return null;
            }
        }
    }
}
