// package hobbiedo.region.type;
//
// import hobbiedo.global.exception.GlobalException;
// import hobbiedo.global.status.ErrorStatus;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
//
// @AllArgsConstructor
// @Getter
// public enum RangeType {
// 	THREE(3),
// 	FIVE(5),
// 	SEVEN(7),
// 	TEN(10);
//
// 	private final int kilometer;
//
// 	public static RangeType toType(int range) {
// 		for (RangeType type : RangeType.values()) {
// 			if (type.getKilometer() == range) {
// 				return type;
// 			}
// 		}
// 		throw new GlobalException(ErrorStatus.NO_EXIST_RANGE_TYPE);
// 	}
// }
