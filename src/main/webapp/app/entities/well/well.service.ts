import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWell } from 'app/shared/model/well.model';

type EntityResponseType = HttpResponse<IWell>;
type EntityArrayResponseType = HttpResponse<IWell[]>;

@Injectable({ providedIn: 'root' })
export class WellService {
    public resourceUrl = SERVER_API_URL + 'api/wells';

    constructor(private http: HttpClient) {}

    create(well: IWell): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(well);
        return this.http
            .post<IWell>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(well: IWell): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(well);
        return this.http
            .put<IWell>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IWell>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IWell[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(well: IWell): IWell {
        const copy: IWell = Object.assign({}, well, {
            licenseDate: well.licenseDate != null && well.licenseDate.isValid() ? well.licenseDate.toJSON() : null,
            dateTimeSpud: well.dateTimeSpud != null && well.dateTimeSpud.isValid() ? well.dateTimeSpud.toJSON() : null,
            dateTimePA: well.dateTimePA != null && well.dateTimePA.isValid() ? well.dateTimePA.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.licenseDate = res.body.licenseDate != null ? moment(res.body.licenseDate) : null;
        res.body.dateTimeSpud = res.body.dateTimeSpud != null ? moment(res.body.dateTimeSpud) : null;
        res.body.dateTimePA = res.body.dateTimePA != null ? moment(res.body.dateTimePA) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((well: IWell) => {
            well.licenseDate = well.licenseDate != null ? moment(well.licenseDate) : null;
            well.dateTimeSpud = well.dateTimeSpud != null ? moment(well.dateTimeSpud) : null;
            well.dateTimePA = well.dateTimePA != null ? moment(well.dateTimePA) : null;
        });
        return res;
    }
}
