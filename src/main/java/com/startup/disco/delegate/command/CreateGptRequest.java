package com.startup.disco.delegate.command;

import com.startup.disco.model.PickDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGptRequest {
    private String query;
}
