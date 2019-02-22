package com.examples.school.app.swing;

import java.awt.EventQueue;
import java.util.concurrent.Callable;

import com.examples.school.guice.SchoolSwingMongoDefaultModule;
import com.examples.school.view.swing.StudentSwingView;
import com.google.inject.Guice;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(mixinStandardHelpOptions = true)
public class SchoolSwingApp implements Callable<Void> {

	@Option(names = { "--mongo-host" }, description = "MongoDB host address")
	private String mongoHost = "localhost";

	@Option(names = { "--mongo-port" }, description = "MongoDB host port")
	private int mongoPort = 27017;

	@Option(names = { "--db-name" }, description = "Database name")
	private String databaseName = "school";

	@Option(names = { "--db-collection" }, description = "Collection name")
	private String collectionName = "student";

	public static void main(String[] args) {
		CommandLine.call(new SchoolSwingApp(), args);
	}

	@Override
	public Void call() throws Exception {
		EventQueue.invokeLater(() -> {
			try {
				Guice.createInjector(
						new SchoolSwingMongoDefaultModule()
							.mongoHost(mongoHost)
							.mongoPort(mongoPort)
							.databaseName(databaseName)
							.collectionName(collectionName))
				.getInstance(StudentSwingView.class)
				.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return null;
	}

}
