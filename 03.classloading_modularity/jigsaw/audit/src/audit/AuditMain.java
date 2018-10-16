package audit;

import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Opens;
import java.lang.module.ResolvedModule;
import java.lang.reflect.InvocationTargetException;
import java.lang.Module;
import java.util.Properties;

public class AuditMain {

	public static void main(String[] args)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException, IOException {

		Module thisModule = AuditMain.class.getModule();

		// iterate over all of the modules in the current layer (the boot one,
		// since we have no other layers defined)
		for (ResolvedModule rm : thisModule.getLayer().configuration().modules()) {
			// the ModuleDescriptor object contains meta information about the
			// module - exports, requires, etc.
			ModuleDescriptor descriptor = rm.reference().descriptor();
			// iterate over all open packages
			for (Opens o : descriptor.opens()) {
				for (String s : o.targets()) {
					// and see if any of them happen to be open to us :)
					if (s.equals(thisModule.getName())) {
						// cool, we need to get this module - it has a package
						// open to the current one
						Module toLoadFrom = thisModule.getLayer().findModule(rm.name()).get();
						String auditClassName = readAuditClassNameFromFile(toLoadFrom, o.source());

						// this is going to work OK, since the package is open
						// to us.
						// alternative, if the package was not open -
						// toLoadFrom.getClassLoader().loadClass(auditClassName)
						Class clazz = Class.forName(auditClassName);
						// clazz.newInstance is deprecated :(
						Object auditObject = clazz.getConstructor().newInstance();
						System.out.println(clazz.getDeclaredMethod("getAuditInformation").invoke(auditObject));
					}
				}
			}
		}
	}

	private static String readAuditClassNameFromFile(Module module, String packageName) throws IOException {
		String resourceName = packageName.replaceAll("\\.", "/") + "/audit.properties";
		InputStream is = module.getResourceAsStream(resourceName);
		Properties props = new Properties();
		props.load(is);
		return props.getProperty("auditInfoClass");
	}
}
