export interface INBPalette {
  id?: number;
  nbPaletteID?: string | null;
  nbPaletteTitle?: string | null;
  nbPaletteType?: string | null;
  nbPaletteColors?: string | null;
  nbLastUpdated?: string | null;
  nbLastUpdatedBy?: string | null;
}

export class NBPalette implements INBPalette {
  constructor(
    public id?: number,
    public nbPaletteID?: string | null,
    public nbPaletteTitle?: string | null,
    public nbPaletteType?: string | null,
    public nbPaletteColors?: string | null,
    public nbLastUpdated?: string | null,
    public nbLastUpdatedBy?: string | null
  ) {}
}

export function getNBPaletteIdentifier(nBPalette: INBPalette): number | undefined {
  return nBPalette.id;
}
