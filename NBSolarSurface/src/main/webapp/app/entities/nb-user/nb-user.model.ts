export interface INBUser {
  id?: number;
  nbUserID?: string | null;
  nbAuthType?: string | null;
  nbPasswordHash?: string | null;
  nbFirstName?: string | null;
  nbLastName?: string | null;
  nbAddress?: string | null;
  nbEmailId?: string | null;
  nbPhone?: string | null;
  nbIsActive?: string | null;
  nbIsSuspended?: string | null;
  nbIsBanished?: string | null;
  nbLastUpdated?: string | null;
  nbLastUpdatedBy?: string | null;
}

export class NBUser implements INBUser {
  constructor(
    public id?: number,
    public nbUserID?: string | null,
    public nbAuthType?: string | null,
    public nbPasswordHash?: string | null,
    public nbFirstName?: string | null,
    public nbLastName?: string | null,
    public nbAddress?: string | null,
    public nbEmailId?: string | null,
    public nbPhone?: string | null,
    public nbIsActive?: string | null,
    public nbIsSuspended?: string | null,
    public nbIsBanished?: string | null,
    public nbLastUpdated?: string | null,
    public nbLastUpdatedBy?: string | null
  ) {}
}

export function getNBUserIdentifier(nBUser: INBUser): number | undefined {
  return nBUser.id;
}
