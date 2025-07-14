public class Main {
    public static void main(String[] args) {
        try {
            FolderApiClient.fetchFolders();
            FolderApiClient.createFolder("Test Folder");
            FolderApiClient.updateFolder("Updated Folder");
            FolderApiClient.deleteFolder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
