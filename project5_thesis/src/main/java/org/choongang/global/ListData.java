package org.choongang.global;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListData<T> {
    private List<T> items = new ArrayList<>(); // 기본값으로 빈 리스트 할당
    private Pagination pagination;
}