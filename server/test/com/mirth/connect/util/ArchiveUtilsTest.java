package com.mirth.connect.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Strings;

public class ArchiveUtilsTest {


    @Test(expected = ZipException.class)
    public void testExtractZipEntryZipSlipWithRelativePath() throws Exception {
        File installTempDir = new File("tests/zipextraction");
        File file = ZipTestUtils.createTempZipFile("../ZipSlip.txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ZipInputStream zis = new ZipInputStream(bis);
        ArchiveUtils.extractArchive(installTempDir, zis);
    }
    
    @Test
    public void testExtractZipNoZipSlip() throws Exception {
        File installTempDir = new File("tests/zipextraction");
        File file = ZipTestUtils.createTempZipFile("ZipSlip.txt");
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ZipInputStream zis = new ZipInputStream(bis);
        List<URL> fileUrls = ArchiveUtils.extractArchive(installTempDir, zis);
        assertFalse(fileUrls.isEmpty());
        File extractedFile = new File("tests/zipextraction", "ZipSlip.txt");
        assertTrue(extractedFile.exists());
    }

    @Test
    public void testExtractEmptyZipFile() throws Exception {
        File installTempDir = new File("tests/zipextraction");
        File file = ZipTestUtils.createTempZipFile(null);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ZipInputStream zis = new ZipInputStream(bis);
        List<URL> fileUrls = ArchiveUtils.extractArchive(installTempDir, zis);
        assertTrue(fileUrls.isEmpty());
    }

    @Before
    public void createTestFolder() {
        File installTempDir = new File("tests/zipextraction");
        if (!installTempDir.exists()) {
            installTempDir.mkdir();
        } else {
            cleanupTestFolder();
        }
    }
    
    @After
    public void cleanupTestFolder() {
        File tempDir = new File("tests/zipextraction"); 
        if (tempDir.exists()) {
            for (File file : tempDir.listFiles()) {
                file.delete();
            }
        }
    }
}
