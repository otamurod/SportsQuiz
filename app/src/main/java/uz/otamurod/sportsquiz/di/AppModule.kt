package uz.otamurod.sportsquiz.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.otamurod.sportsquiz.database.dao.QuestionsDao
import uz.otamurod.sportsquiz.database.dao.SportsDao
import uz.otamurod.sportsquiz.database.db.AppDatabase
import uz.otamurod.sportsquiz.repository.RoomRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideSportsDao(appDatabase: AppDatabase): SportsDao {
        return appDatabase.sportsDao()
    }

    @Provides
    @Singleton
    fun provideQuestionsDao(appDatabase: AppDatabase): QuestionsDao {
        return appDatabase.questionsDao()
    }

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "quiz"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .createFromAsset("database/sports.db")
            .build()

    @Provides
    @Singleton
    fun provideRoomRepository(
        sportsDao: SportsDao,
        questionsDao: QuestionsDao
    ): RoomRepository {
        return RoomRepository(sportsDao, questionsDao)
    }
}