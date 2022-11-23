import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	// https://github.com/etiennestuder/gradle-jooq-plugin#compatibility
	id("nu.studer.jooq") version "8.0"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	// for "gradle-jooq-plugin" https://github.com/etiennestuder/gradle-jooq-plugin#compatibility
	implementation("org.jooq:jooq:3.17.5")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.13.2")

	jooqGenerator("org.postgresql:postgresql")
	// https://github.com/etiennestuder/gradle-jooq-plugin/issues/207
	jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
}

jooq {
	// version.set(dependencyManagement.importedProperties["jooq.version"])
	version.set("3.17.5")
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

	configurations {
		create("main") {
			// ref https://github.com/etiennestuder/gradle-jooq-plugin#generating-the-jooq-sources
			// ref https://www.greptips.com/posts/1350/#jooq-configurations
            generateSchemaSourceOnCompilation.set(false)
			jooqConfiguration.apply {
				jdbc.apply {
					driver = "org.postgresql.Driver"
//					url = System.getenv("POSTGRES_URL")
//					user = System.getenv("POSTGRES_USER")
//					password = System.getenv("POSTGRES_PASSWORD")
					url = "jdbc:postgresql://localhost:5432/household-expenses"
					user = "postgres"
					password = "password"
				}
				generator.apply {
					name = "org.jooq.codegen.KotlinGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "public"
						excludes = "flyway_schema_history"
					}
					generate.apply {
						isDeprecated = false
						isTables = true
						// isRecords = true
						// isImmutablePojos = true
						// isFluentSetters = true
					}
					target.apply {
						packageName = "com.example.householdExpenses.jooq.codegen"
						// directory = "${buildDir}/generated/source/jooq/main"
						directory = "build/generated-src/jooq/"
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}

// test の task 実行時に generateJooq を実行しないための設定
// 実際は、 `generateSchemaSourceOnCompilation.set(false)` で無効にできたため不要
//tasks {
//    test {
//        findByName("generateJooq")?.enabled = false
//    }
//}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
