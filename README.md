# GeoQuiz

## About project

GeoQuiz is the project suggested in the book Android Programming: The Big Nerd Ranch Guide (4th edition) 

This is a quiz that tests a person's geographical knowledge. 
You are asked to answer 6 statements, which can be either true or false. 

## Project structure
- MainActivity.kt - It's a base activity where man answering the questions
- Question.kt - It's a data class describing a question and the correct answer
- QuizViewModel.kt - It's a ViewModel which saving instance state MainActivity when we rotate the phone
- CheatAcitivy.kt - It's a secondary activity in which a person can look up the correct answer to a question if they are unsure of their knowledge
