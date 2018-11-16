import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWell } from 'app/shared/model/well.model';
import { WellService } from './well.service';

@Component({
    selector: 'jhi-well-delete-dialog',
    templateUrl: './well-delete-dialog.component.html'
})
export class WellDeleteDialogComponent {
    well: IWell;

    constructor(private wellService: WellService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wellService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'wellListModification',
                content: 'Deleted an well'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-well-delete-popup',
    template: ''
})
export class WellDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ well }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WellDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.well = well;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
