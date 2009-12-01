package com.izforge.izpack.compiler;

import com.izforge.izpack.compiler.CompilerConfig;
import org.hamcrest.core.Is;
import com.izforge.izpack.AssertionHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.StringContains;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test for an Izpack compilation
 */
public class CompilationTest {

    private File baseDir = new File(getClass().getClassLoader().getResource("samples").getFile());
    private File installerFile = new File(getClass().getClassLoader().getResource("samples/helloAndFinish.xml").getFile());
    private File out = new File(baseDir, "out.jar");

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void cleanFiles() {
        assertThat(baseDir.exists(),Is.is(true));
        out.delete();
    }

    @Test
    public void compilerShouldCompile() throws Exception {
        CompilerConfig c = new CompilerConfig(installerFile.getAbsolutePath(), baseDir.getAbsolutePath(), "default", out.getAbsolutePath());
        c.executeCompiler();
        assertThat(c.wasSuccessful(), Is.is(true));
    }

    @Test
    public void installerShouldContainInstallerClass() throws Exception {
        CompilerConfig c = new CompilerConfig(installerFile.getAbsolutePath(), baseDir.getAbsolutePath(), "default", out.getAbsolutePath());
        c.executeCompiler();
        AssertionHelper.assertZipContainsMatch(out, StringContains.containsString("Installer.class"));
    }

    @Test
    public void installerShouldContainClasses() throws Exception {
        CompilerConfig c = new CompilerConfig(installerFile.getAbsolutePath(), baseDir.getAbsolutePath(), "default", out.getAbsolutePath());
        c.executeCompiler();
        AssertionHelper.assertZipContainsMatch(out, StringContains.containsString("Debug.class"));
        AssertionHelper.assertZipContainsMatch(out, StringContains.containsString("ComponentFactory.class"));
    }

    @Test
    public void installerShouldContainImages() throws Exception {
        CompilerConfig c = new CompilerConfig(installerFile.getAbsolutePath(), baseDir.getAbsolutePath(), "default", out.getAbsolutePath());
        c.executeCompiler();
        AssertionHelper.assertZipContainsMatch(out, Is.is("img/JFrameIcon.png"));
    }


}