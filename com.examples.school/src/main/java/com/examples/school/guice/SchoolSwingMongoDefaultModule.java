package com.examples.school.guice;

import com.examples.school.controller.SchoolController;
import com.examples.school.repository.StudentRepository;
import com.examples.school.repository.mongo.StudentMongoRepository;
import com.examples.school.view.swing.StudentSwingView;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.mongodb.MongoClient;

public class SchoolSwingMongoDefaultModule extends AbstractModule {

	private String mongoHost = "localhost";

	private int mongoPort = 27017;

	private String databaseName = "school";

	private String collectionName = "student";

	public SchoolSwingMongoDefaultModule mongoHost(String mongoHost) {
		this.mongoHost = mongoHost;
		return this;
	}

	public SchoolSwingMongoDefaultModule mongoPort(int mongoPort) {
		this.mongoPort = mongoPort;
		return this;
	}

	public SchoolSwingMongoDefaultModule databaseName(String databaseName) {
		this.databaseName = databaseName;
		return this;
	}

	public SchoolSwingMongoDefaultModule collectionName(String collectionName) {
		this.collectionName = collectionName;
		return this;
	}

	@Override
	protected void configure() {
		bind(String.class).annotatedWith(MongoHost.class).toInstance(mongoHost);
		bind(Integer.class).annotatedWith(MongoPort.class).toInstance(mongoPort);
		bind(String.class).annotatedWith(MongoDbName.class).toInstance(databaseName);
		bind(String.class).annotatedWith(MongoCollectionName.class).toInstance(collectionName);

		bind(StudentRepository.class).to(StudentMongoRepository.class);

		install(new FactoryModuleBuilder()
				.implement(SchoolController.class, SchoolController.class)
				.build(SchoolControllerFactory.class));
	}

	@Provides
	MongoClient mongoClient(@MongoHost String host, @MongoPort int port) {
		return new MongoClient(host, port);
	}

	@Provides
	StudentSwingView studentView(SchoolControllerFactory schoolControllerFactory) {
		StudentSwingView view = new StudentSwingView();
		view.setSchoolController(schoolControllerFactory.create(view));
		return view;
	}
}
