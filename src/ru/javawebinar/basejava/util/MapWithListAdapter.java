package ru.javawebinar.basejava.util;

import ru.javawebinar.basejava.model.Entry;
import ru.javawebinar.basejava.model.ListOfEntry;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class MapWithListAdapter extends XmlAdapter<ListOfEntry, Map<SectionType, List<Section>>> {
    @Override
    public Map<SectionType, List<Section>> unmarshal(ListOfEntry listOfEntry) throws Exception {
        Map<SectionType, List<Section>> map = new EnumMap<>(SectionType.class);
        for (Entry entry : listOfEntry.getList()) {
            map.put(entry.getKey(), entry.getSections());
        }
        return map;
    }

    @Override
    public ListOfEntry marshal(Map<SectionType, List<Section>> map) throws Exception {
        ListOfEntry entries = new ListOfEntry();
        for (Map.Entry<SectionType, List<Section>> mapEntry : map.entrySet()) {
            Entry entry = new Entry();
            entry.setKey(mapEntry.getKey());
            entry.getSections().addAll(mapEntry.getValue());
            entries.getList().add(entry);
        }
        return entries;
    }
}