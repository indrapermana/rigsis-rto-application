import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWellbore } from 'app/shared/model/wellbore.model';
import { WellboreService } from './wellbore.service';

@Component({
    selector: 'jhi-wellbore-delete-dialog',
    templateUrl: './wellbore-delete-dialog.component.html'
})
export class WellboreDeleteDialogComponent {
    wellbore: IWellbore;

    constructor(private wellboreService: WellboreService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wellboreService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'wellboreListModification',
                content: 'Deleted an wellbore'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wellbore-delete-popup',
    template: ''
})
export class WellboreDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wellbore }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WellboreDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.wellbore = wellbore;
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
