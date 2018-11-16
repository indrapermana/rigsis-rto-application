import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IWell } from 'app/shared/model/well.model';
import { WellService } from './well.service';

@Component({
    selector: 'jhi-well-update',
    templateUrl: './well-update.component.html'
})
export class WellUpdateComponent implements OnInit {
    well: IWell;
    isSaving: boolean;
    licenseDate: string;
    dateTimeSpud: string;
    dateTimePA: string;

    constructor(private wellService: WellService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ well }) => {
            this.well = well;
            this.licenseDate = this.well.licenseDate != null ? this.well.licenseDate.format(DATE_TIME_FORMAT) : null;
            this.dateTimeSpud = this.well.dateTimeSpud != null ? this.well.dateTimeSpud.format(DATE_TIME_FORMAT) : null;
            this.dateTimePA = this.well.dateTimePA != null ? this.well.dateTimePA.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.well.licenseDate = this.licenseDate != null ? moment(this.licenseDate, DATE_TIME_FORMAT) : null;
        this.well.dateTimeSpud = this.dateTimeSpud != null ? moment(this.dateTimeSpud, DATE_TIME_FORMAT) : null;
        this.well.dateTimePA = this.dateTimePA != null ? moment(this.dateTimePA, DATE_TIME_FORMAT) : null;
        if (this.well.id !== undefined) {
            this.subscribeToSaveResponse(this.wellService.update(this.well));
        } else {
            this.subscribeToSaveResponse(this.wellService.create(this.well));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWell>>) {
        result.subscribe((res: HttpResponse<IWell>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
