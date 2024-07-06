package it.gagagio.bondsearchtool.euronext.model;

import java.util.List;

public record BondResponse(int iTotalRecords, int iTotalDisplayRecords, List<List<String>> aaData) {
}
