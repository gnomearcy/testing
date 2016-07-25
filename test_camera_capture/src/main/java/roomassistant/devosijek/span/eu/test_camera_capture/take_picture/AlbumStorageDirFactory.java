package roomassistant.devosijek.span.eu.test_camera_capture.take_picture;

import java.io.File;

abstract class AlbumStorageDirFactory {
	public abstract File getAlbumStorageDir(String albumName);
}
