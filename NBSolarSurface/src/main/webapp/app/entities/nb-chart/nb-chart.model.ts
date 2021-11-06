export interface INBChart {
  id?: number;
  nbChartID?: string | null;
  nbChartTitle?: string | null;
  nbChartType?: string | null;
  nbChartParams?: string | null;
  nbLastUpdated?: string | null;
  nbLastUpdatedBy?: string | null;
}

export class NBChart implements INBChart {
  constructor(
    public id?: number,
    public nbChartID?: string | null,
    public nbChartTitle?: string | null,
    public nbChartType?: string | null,
    public nbChartParams?: string | null,
    public nbLastUpdated?: string | null,
    public nbLastUpdatedBy?: string | null
  ) {}
}

export function getNBChartIdentifier(nBChart: INBChart): number | undefined {
  return nBChart.id;
}
