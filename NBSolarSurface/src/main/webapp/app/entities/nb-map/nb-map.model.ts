export interface INBMap {
  id?: number;
  nbID?: string | null;
  nbName?: string | null;
  nbOwner?: string | null;
  nbOwnerPrivateKey?: string | null;
  nbOwnerPublicKey?: string | null;
  nbMapPublishMethod?: string | null;
  nbSubscriptionDate?: string | null;
  nbSubscriptionLastDate?: string | null;
  nbLastUpdated?: string | null;
  nbLastUpdatedBy?: string | null;
}

export class NBMap implements INBMap {
  constructor(
    public id?: number,
    public nbID?: string | null,
    public nbName?: string | null,
    public nbOwner?: string | null,
    public nbOwnerPrivateKey?: string | null,
    public nbOwnerPublicKey?: string | null,
    public nbMapPublishMethod?: string | null,
    public nbSubscriptionDate?: string | null,
    public nbSubscriptionLastDate?: string | null,
    public nbLastUpdated?: string | null,
    public nbLastUpdatedBy?: string | null
  ) {}
}

export function getNBMapIdentifier(nBMap: INBMap): number | undefined {
  return nBMap.id;
}
