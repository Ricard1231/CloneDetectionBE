package com.example.clonedetection.services;

import com.example.clonedetection.constants.Constant;
import com.example.clonedetection.dtos.response.GenerateSnippetsResponse;
import com.example.clonedetection.models.File;
import com.example.clonedetection.models.Snippet;
import com.example.clonedetection.models.SnippetPair;
import com.example.clonedetection.repositories.FileRepository;
import com.example.clonedetection.repositories.SnippetPairRepository;
import com.example.clonedetection.repositories.SnippetRepository;
import com.example.clonedetection.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SnippetServiceImpl implements SnippetService {

    private final SnippetRepository snippetRepository;

    private final SnippetPairRepository snippetPairRepository;

    private final FileRepository fileRepository;

    @Value("${github-personal-access-token:}")
    private String token;

    @Override
    public GenerateSnippetsResponse generateRandom() {
        try {
            Map<String, String> datasetRepoHeaders = new HashMap<>();

            if (!token.equals("")) {
                datasetRepoHeaders.put("Authorization", token);
            }

            String[] languages = new String[] {"dataset_c", "dataset_cpp"};
            int[][] range = new int[][] {new int[] {20, 99}, new int[] {1, 100}};
            String[] source = new String[] {"private", "open_source"};

            int languageIndex = (int) (Math.random() * languages.length);
            int datasetNum1 = range[languageIndex][0] + (int) (Math.random() * ((range[languageIndex][1] - range[languageIndex][0]) + 1));
            int sourceIndex1 = (int) (Math.random() * source.length);

            String path1 = languages[languageIndex] + "/" + datasetNum1 + "/" + source[sourceIndex1];

            Optional<File> optionalFile1 = fileRepository.findByPath(path1);

            File file1, file2;

            Snippet snippet1, snippet2;

            if (optionalFile1.isPresent()) {
                file1 = optionalFile1.get();
            } else {
                Map<String, Object> map = Common.getMapFromStream(Common.getResponseStreamFromUrl(new URL(Constant.DATASET_REPO_URL + path1), datasetRepoHeaders));
                String response = Common.getStringFromStream(Common.getResponseStreamFromUrl(new URL(map.get("download_url").toString()), new HashMap<>()));
                file1 = fileRepository.save(new File(path1));
                List<String> functions = Common.extractFunctionsFromFile(response);

                functions.forEach(function -> {
                    snippetRepository.save(new Snippet(function, file1));
                });
            }

            List<Snippet> file1Snippets = snippetRepository.findAllByFile(file1);
            snippet1 = Common.getRandomItemFromList(file1Snippets);

            int datasetNum2 = range[languageIndex][0] + (int) (Math.random() * ((range[languageIndex][1] - range[languageIndex][0]) + 1));
            int sourceIndex2 = (int) (Math.random() * source.length);

            String path2 = languages[languageIndex] + "/" + datasetNum2 + "/" + source[sourceIndex2];

            Optional<File> optionalFile2 = fileRepository.findByPath(path2);

            if (optionalFile2.isPresent()) {
                file2 = optionalFile2.get();
            } else {
                Map<String, Object> map = Common.getMapFromStream(Common.getResponseStreamFromUrl(new URL(Constant.DATASET_REPO_URL + path2), datasetRepoHeaders));
                String response = Common.getStringFromStream(Common.getResponseStreamFromUrl(new URL(map.get("download_url").toString()), new HashMap<>()));
                file2 = fileRepository.save(new File(path2));
                List<String> functions = Common.extractFunctionsFromFile(response);

                functions.forEach(function -> {
                    snippetRepository.save(new Snippet(function, file2));
                });
            }

            List<Snippet> file2Snippets = snippetRepository.findAllByFile(file2);
            snippet2 = Common.getRandomItemFromList(file2Snippets);

            SnippetPair snippetPair = snippetPairRepository.findBySnippet1AndSnippet2(snippet1, snippet2)
                    .orElse(snippetPairRepository.save(
                            SnippetPair.builder()
                            .snippet1(snippet1)
                            .snippet2(snippet2)
                            .build()));

            return GenerateSnippetsResponse.builder()
                    .pairId(snippetPair.getId())
                    .snippet1(Objects.requireNonNull(snippet1).getCode())
                    .snippet2(Objects.requireNonNull(snippet2).getCode())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
