package hobbiedo.survey.domain;

import hobbiedo.global.base.BaseEntity;
import hobbiedo.survey.type.QuestionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HobbySurvey extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 1000)
	private String question;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private QuestionType questionType;

	@Builder
	public HobbySurvey(String question, QuestionType questionType) {
		this.question = question;
		this.questionType = questionType;
	}
}
